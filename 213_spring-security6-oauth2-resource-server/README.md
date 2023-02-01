# spring-boot-starter-oauth2-resource-server

# POST http://localhost:8080/authenticate

- 手間を省くために、Basic Auth を使っている。
- Basic Auth を通過後、JwtAuthenticationController で JWT をつけている。

# GET http://localhost:8080/todos

- 要認証で、誰でもアクセス可能。
- JwtAuthenticationProvider が token のチェックをやってくれているもよう。

# GET http://localhost:8080/admin

- ADMIN でロールを登録すると、authority は ROLE_ADMIN になる。
- JWT を作るときは claim に scope をセットするので、hasAuthority("SCOPE_ROLE_ADMIN")になる。

```
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
```

```
http.authorizeHttpRequests()
    .requestMatchers("/admin").hasAuthority("SCOPE_ROLE_ADMIN")
    .requestMatchers("/user").hasAuthority("SCOPE_ROLE_USER")
    .anyRequest().authenticated();
```

# GET http://localhost:8080/user

- USER でロールを登録すると、authority は ROLE_USER になる。
- JWT を作るときは claim に scope をセットするので、hasAuthority("SCOPE_ROLE_USER")になる。

# 不明点

Tomcat を再起動すると、有効期限前なのに、Token が無効化されてしまう。
