package com.afcruz.book_network.config;

import com.afcruz.book_network.user.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class ApplicationAuditAware implements AuditorAware<Integer> {
    @Override
    @NonNull
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            var user = (User) authentication.getPrincipal();

            return Optional.ofNullable(user.getId());
        }

        return Optional.empty();
    }
}