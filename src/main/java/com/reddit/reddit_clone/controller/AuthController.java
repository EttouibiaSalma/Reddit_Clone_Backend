package com.reddit.reddit_clone.controller;

import com.reddit.reddit_clone.dto.AuthenticationResponse;
import com.reddit.reddit_clone.dto.LoginRequest;
import com.reddit.reddit_clone.dto.RegistrationRequest;
import com.reddit.reddit_clone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request){
        authService.register(request);
        return new ResponseEntity<>("Registration success !", HttpStatus.OK);

    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyToken(token);
        return new ResponseEntity<>("Your account has been verified", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
