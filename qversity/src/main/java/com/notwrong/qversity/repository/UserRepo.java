package com.notwrong.qversity.repository;

import com.notwrong.qversity.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Optional<Object> findByNickname(String nickname);
}