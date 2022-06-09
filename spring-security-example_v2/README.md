# SpringSecurity

# POST http://localhost:8080/signup

- CSRF を disabled にしているのでアクセス可能。
- BCryptPasswordEncoder を使って INSERT しているだけ。

```
http.csrf().ignoringAntMatchers("/signup");
```

# GET http://localhost:8080/welcome

- デフォルトのログインページが表示される。UserDetailsService でログインを実装。
- BCryptPasswordEncoder を@Bean しているので、DaoAuthenticationProvider が良い感じに処理してくれる。

# POST http://localhost:8080/hello

- CSRF が効いているのでアクセス不可。
- CSRF エラーが発生した場合、SpringSecurity はデバッグでログを出すので要注意。

```
<logger name="org.springframework.security" level="DEBUG">
    <appender-ref ref="STDOUT" />
    <appender-ref ref="APPLICATION_LOG" />
</logger>
```

```
Invalid CSRF token found for http://localhost:8080/hello
```
