package com.notwrong.qversity.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notwrong.qversity.config.JwtTokenProvider;
import com.notwrong.qversity.entity.SocialLogin;
import com.notwrong.qversity.entity.Users;
import com.notwrong.qversity.repository.SocialLoginRepository;
import com.notwrong.qversity.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final SocialLoginRepository socialLoginRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;


    public AuthService(UserRepo userRepo,
                       SocialLoginRepository socialLoginRepository,
                       JwtTokenProvider jwtTokenProvider,
                       RestTemplate restTemplate,
                       ObjectMapper objectMapper) {
        this.userRepo = userRepo;
        this.socialLoginRepository = socialLoginRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

     //Google OAuth2 로그인 처리: 인가 코드로 Access Token을 요청하고 사용자 정보를 받아 로그인/회원가입 처리 후 JWT 토큰을 발급합니다.
     //code: Google Authorization 서버로부터 받은 인가 코드, return: 생성된 JWT 토큰
    @Transactional // DB 트랜잭션 관리
    public String googleLogin(String code) {

        //  인가 코드로 Access Token 요청
        String accessToken = getGoogleAccessToken(code);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Google Access Token 획득 실패 또는 토큰이 비어있습니다.");
        }

        // Access Token으로 사용자 정보 요청
        GoogleUserInfo googleUserInfo = getGoogleUserInfo(accessToken);
        if (googleUserInfo == null || googleUserInfo.getId() == null || googleUserInfo.getId().isEmpty()) {
            throw new RuntimeException("Google 사용자 정보 획득 실패 또는 사용자 ID가 없습니다.");
        }

        // 3. 사용자 정보로 로그인/회원가입 처리
        return processSocialLogin(
                "GOOGLE",
                googleUserInfo.getId(),
                googleUserInfo.getEmail(),
                googleUserInfo.getName(),
                googleUserInfo.getPicture()
        );
    }

    // Google Access Token 요청
    private String getGoogleAccessToken(String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "code=" + code +
                "&client_id=" + googleClientId +
                "&client_secret=" + googleClientSecret +
                "&redirect_uri=" + googleRedirectUri + 
                "&grant_type=authorization_code";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUrl, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                return root.path("access_token").asText(null);
            } else {
                System.err.println("Google Access Token 요청 실패. 상태: " + response.getStatusCode() + ", 응답: " + response.getBody());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Google Access Token 획득 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Google 사용자 정보 요청
    private GoogleUserInfo getGoogleUserInfo(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    userInfoUrl, HttpMethod.GET, request, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                return new GoogleUserInfo(
                        root.path("sub").asText(null),
                        root.path("email").asText(null),
                        root.path("name").asText(null),
                        root.path("picture").asText(null)
                );
            } else {
                System.err.println("Google 사용자 정보 요청 실패. 상태: " + response.getStatusCode() + ", 응답: " + response.getBody());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Google 사용자 정보 획득 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Google 사용자 정보 매핑을 위한 내부 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class GoogleUserInfo {
        private String id; 
        private String email;
        private String name;
        private String picture; // 프로필 이미지 URL로 사용가능
    }

    // 소셜 로그인 처리 로직: DB에 사용자 정보 저장/업데이트 후 JWT 발급
    private String processSocialLogin(String provider, String providerId, String email, String displayName, String profileImageUrl) {
        // social_login 테이블에서 기존 소셜 계정 조회
        Optional<SocialLogin> existingSocialLogin = socialLoginRepository.findByProviderAndProviderId(provider, providerId);

        Users user;

        if (existingSocialLogin.isPresent()) {
            // 기존 소셜 계정이 있다면: 해당 user_id로 로그인 처리
            SocialLogin socialLogin = existingSocialLogin.get();
            user = socialLogin.getUser();

            // social_login 정보 업데이트
            socialLogin.setEmail(email);
            socialLoginRepository.save(socialLogin);

            System.out.println("기존 사용자 로그인: User ID=" + user.getUserId() + ", Nickname=" + user.getNickname());

        } else {
            // 새로운 소셜 계정이라면: 사용자 생성 및 연결
            String initialNickname = (displayName != null && !displayName.isEmpty()) ? displayName : "User_" + UUID.randomUUID().toString().substring(0, 8);
            String finalNickname = generateUniqueNickname(initialNickname);

            // Users 엔티티 생성 및 저장
            user = new Users(finalNickname, profileImageUrl);
            userRepo.save(user);

            // SocialLogin 엔티티 생성 및 저장
            SocialLogin newSocialLogin = new SocialLogin();
            newSocialLogin.setUser(user);
            newSocialLogin.setProvider(provider);
            newSocialLogin.setProviderId(providerId);
            newSocialLogin.setEmail(email);
            socialLoginRepository.save(newSocialLogin);

            System.out.println("새로운 사용자 가입: User ID=" + user.getUserId() + ", Nickname=" + user.getNickname());
        }

        // JWT 토큰 발급 및 반환
        return jwtTokenProvider.generateToken(user.getUserId().toString());
    }

    // 닉네임 중복 처리 - 수
    private String generateUniqueNickname(String baseNickname) {
        String nickname = baseNickname;
        int count = 0;
        while (userRepo.findByNickname(nickname).isPresent()) {
            count++;
            nickname = baseNickname + "_" + count;
            if (count > 100) { // 무한 루프 방지
                nickname = "User_" + UUID.randomUUID().toString().substring(0, 8);
                break;
            }
        }
        return nickname;
    }
}