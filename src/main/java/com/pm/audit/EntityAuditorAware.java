package com.pm.audit;

import com.pm.entity.UserEntity;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
public class EntityAuditorAware implements AuditorAware<UserEntity> {
    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        return Optional.of(new UserEntity(1));
    }

}
