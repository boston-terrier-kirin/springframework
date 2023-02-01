package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @PostMapping("/authenticate")
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(createToken(authentication));
    }

    private String createToken(Authentication authentication) {
        String scope = createScope(authentication);
        var claims = JwtClaimsSet.builder()
                                    .issuer("self")
                                    .issuedAt(Instant.now())
                                    .expiresAt(Instant.now().plusSeconds(60 * 15))
                                    .subject(authentication.getName())
                                    // ここはscopeが正しいっぽい。
                                    // roleにすると、authoritiesがつかない。
                                    // oauth2的にはscopeが正しくて、SCOPE_ROLE_ADMINになってしまう。
                                    .claim("scope", scope)
                                    .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(parameters).getTokenValue();
    }

    private String createScope(Authentication authentication) {
        // ロールをADMINで登録しておくと、authorityはROLE_ADMINになる。
        return authentication.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.joining(" "));
    }
}

record JwtResponse(String token) {}