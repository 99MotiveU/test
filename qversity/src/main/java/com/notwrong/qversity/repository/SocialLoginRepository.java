package com.notwrong.qversity.repository;

import com.notwrong.qversity.entity.SocialLogin; // SocialLogin 엔티티 사용
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
    Optional<SocialLogin> findByProviderAndProviderId(String provider, String providerId);
    // provider와 provider_id로 소셜 계정 조회
}