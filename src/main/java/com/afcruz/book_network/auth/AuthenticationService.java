package com.afcruz.book_network.auth;

import com.afcruz.book_network.email.EmailService;
import com.afcruz.book_network.email.EmailTemplateName;
import com.afcruz.book_network.role.RoleRepository;
import com.afcruz.book_network.user.Token;
import com.afcruz.book_network.user.TokenRepository;
import com.afcruz.book_network.user.User;
import com.afcruz.book_network.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Value("${spring.mailing.frontend.activation-url}")
    private String activationUrl;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final EmailService emailService;

    public void registerUser(RegistrationRequest registrationRequest) {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER was not found"));

        try {
            var user = User.builder()
                    .firstname(registrationRequest.getFirstname())
                    .lastname(registrationRequest.getLastname())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .birthDate(LocalDate.parse(registrationRequest.getBirthDate().toString(), DateTimeFormatter.ISO_DATE))
                    .isAccountLocked(false)
                    .isEnabled(false)
                    .roles(List.of(userRole))
                    .build();

            userRepository.save(user);

            sendValidationEmail(user);

        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT, activationUrl,
                newToken, "Account Activation");
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationToken(6);

        try {
            var token = Token.builder()
                    .token(generatedToken)
                    .createAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
                    .user(user)
                    .build();

            tokenRepository.save(token);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }

        return generatedToken;
    }

    private String generateActivationToken(int length) {
        return String.valueOf(new SecureRandom().nextInt(length * 100000));
    }
}
