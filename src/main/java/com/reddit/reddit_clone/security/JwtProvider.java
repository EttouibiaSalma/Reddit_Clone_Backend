package com.reddit.reddit_clone.security;

import com.reddit.reddit_clone.exception.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resource = getClass().getResourceAsStream("/reddit.jks");
            keyStore.load(resource, "secret".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new ApplicationException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("reddit", "secret".toCharArray());
        } catch (Exception e) {
            throw new ApplicationException("Exception occurred while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String token){
        parser().setSigningKey(getPublicKey()).parseClaimsJwt(token);
        return true;
    }

    private PublicKey getPublicKey(){
        try {
            return keyStore.getCertificate("reddit").getPublicKey();
        } catch (KeyStoreException e) {
            throw new ApplicationException("Exception occurred when retrieving public key.");
        }
    }

    public String getUsernameFromJwt(String token){
        Claims claims = parser()
                .setSigningKey(getPrivateKey())
                .parseClaimsJwt(token)
                .getBody();
        return claims.getSubject();
    }
}
