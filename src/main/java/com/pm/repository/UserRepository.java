package com.pm.repository;

import com.pm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByAccount(String account);
}
