package com.notwrong.qversity.controller;

import com.notwrong.qversity.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Google 인가 코드(Authorization Code)를 받아 로그인 처리
    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            // AuthService를 통해 Google 로그인 처리 및 JWT 토큰 발급
            String jwtToken = authService.googleLogin(request.getCode());
            return ResponseEntity.ok(new LoginResponse(jwtToken, "Google login 성공"));
        } catch (RuntimeException e) {
            System.err.println("Google 로그인 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, "Google login failed: " + e.getMessage()));
        }
    }

    // 클라이언트로부터 받을 요청
    @Getter
    @Setter
    @NoArgsConstructor
    public static class GoogleLoginRequest {
        private String code; // Google에서 받은 인가 코드
    }

    // 클라이언트에게 보낼 응답
    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String message;
    }
    
}