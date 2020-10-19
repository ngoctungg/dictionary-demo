package com.pm.repository;

import com.pm.entity.RoleEntity;
import com.pm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
}
