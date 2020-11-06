package com.pm.repository;

import com.pm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByAccountAndActiveIsTrue(String account);

    @Query("select u from UserEntity u order by u.active DESC ")
    List<UserEntity> getAllAndSortByActive();
}
