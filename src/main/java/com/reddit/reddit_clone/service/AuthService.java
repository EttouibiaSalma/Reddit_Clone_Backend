package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.dto.RegistrationRequest;
import com.reddit.reddit_clone.model.NotificationEmail;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.model.VerificationToken;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void register(RegistrationRequest registrationRequest){
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(encoder.encode(registrationRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendEmail(new NotificationEmail("Activation mail", user.getEmail(), "Thank you for signing up to Reddit, " +
                "Please click the link bellow to activate your account: http://localhost:8080/api/auth/activateAccount" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
