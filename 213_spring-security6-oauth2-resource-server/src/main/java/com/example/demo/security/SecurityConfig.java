package com.example.demo.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
public class SecurityConfig {
    /**
     * AuthenticationManager
     * 　↓
     * JwtAuthenticationProvider
     * 　↓
     * JdbcUserDetailsManager(UserDetailsService).loadUsersByUsername
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /**
         * ■権限チェックしているところ。
         * AuthorizationFilter.doFilter
         * RequestMatcherDelegatingAuthorizationManager.check
         * AuthorityAuthorizationManager.check
         */

        /**
         * ADMINでロールを登録すると、authorityはROLE_ADMINになる。
         * JWTを作るときはclaimにscopeをセットするので、hasAuthority("SCOPE_ROLE_ADMIN")になる。
         */

        http.authorizeHttpRequests()
            .requestMatchers("/admin").hasAuthority("SCOPE_ROLE_ADMIN")
            .requestMatchers("/user").hasAuthority("SCOPE_ROLE_USER")
            .anyRequest().authenticated();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic();
        http.csrf().disable();

        // h2がframeを使っているので、sameOriginはOKにする。
        http.headers().frameOptions().sameOrigin();

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource, PasswordEncoder passwordEncoder) {
        var kirin = User.withUsername("kirin")
                .password(passwordEncoder.encode("test"))
                .authorities("SCOPE_ROLE_ADMIN")
                .roles("ADMIN").build();

        var kuroro = User.withUsername("kuroro")
                .password(passwordEncoder.encode("test"))
                .authorities("SCOPE_ROLE_USER")
                .roles("USER").build();

        /**
         * JdbcUserDetailsManager
         *   ↓
         * UserDetailsManager
         *   ↓
         * UserDetailsService
         */

        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(kirin);
        jdbcUserDetailsManager.createUser(kuroro);

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                            .privateKey(keyPair.getPrivate())
                            .keyID(UUID.randomUUID().toString())
                            .build();
    }

    @Bean
    public JWKSource jwkSource(RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);
        var jwkSource = new JWKSource() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
                return jwkSelector.select(jwkSet);
            }
        };

        return jwkSource;
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder
                    .withPublicKey(rsaKey.toRSAPublicKey())
                    .build();
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
}
