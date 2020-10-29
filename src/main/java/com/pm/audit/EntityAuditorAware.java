package com.pm.audit;

import com.pm.entity.UserEntity;
import com.pm.model.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
public class EntityAuditorAware implements AuditorAware<UserEntity> {
    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.of(customUserDetails.getUser());
    }

}
