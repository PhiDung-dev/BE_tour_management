package com.example.BE_tour_management.service;

import com.example.BE_tour_management.dto.request.AuthenticationRequest;
import com.example.BE_tour_management.dto.request.IntrospectRequest;
import com.example.BE_tour_management.dto.response.AuthenticationResponse;
import com.example.BE_tour_management.dto.response.IntrospectResponse;
import com.example.BE_tour_management.entity.Account;
import com.example.BE_tour_management.exception.AppException;
import com.example.BE_tour_management.exception.ErrorCode;
import com.example.BE_tour_management.repository.AccountRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    AccountRepository accountRepository;
    @Value("${jwt.secret-key}")
    @NonFinal
    String secretKey;

    public AuthenticationResponse login(AuthenticationRequest request) throws JOSEException {
        if(!accountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        Account account = accountRepository.findByUsername(request.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String token=null;
        if(passwordEncoder.matches(request.getPassword(), account.getPassword())){
            token = generateToken(request.getUsername());
        }
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private String generateToken(String username) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("service")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();
        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        JWSSigner signer = new MACSigner(secretKey);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        return IntrospectResponse.builder()
                .valid(verifyToken(request.getToken()))
                .build();
    }

    private boolean verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier jwsVerifier = new MACVerifier(secretKey);
        boolean verified = signedJWT.verify(jwsVerifier);
        Date expTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return verified && expTime.after(new Date());
    }

}
