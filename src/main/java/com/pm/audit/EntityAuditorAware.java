package com.pm.audit;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;
public class EntityAuditorAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(1l);
    }

}
