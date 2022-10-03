package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.dto.AuthenticationResponse;
import com.reddit.reddit_clone.dto.LoginRequest;
import com.reddit.reddit_clone.dto.RegistrationRequest;
import com.reddit.reddit_clone.exception.ApplicationException;
import com.reddit.reddit_clone.model.NotificationEmail;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.model.VerificationToken;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.repository.VerificationTokenRepository;
import com.reddit.reddit_clone.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

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
                "Please click the link bellow to activate your account: http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyToken(String token){
        Optional<VerificationToken> assignedToken = verificationTokenRepository.findByToken(token);
        assignedToken.orElseThrow(() -> new ApplicationException("Invalid token"));
        fetchUserAndEnable(assignedToken.get());
    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken assignedToken) {
        String username = assignedToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ApplicationException("user Not found" + username));
        user.setEnabled(true);
    }
    public AuthenticationResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getPassword(),
                loginRequest.getUsername()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
