# SpringSecurity

# GET http://localhost:8080/welcome
- デフォルトのログインページが表示される。UserDetailsServiceでログインを実装。

# POST http://localhost:8080/signup
- CSRFをdisabledにしているのでアクセス可能。

```
http.csrf().ignoringAntMatchers("/signup");
```

# POST http://localhost:8080/hello
- CSRFが効いているのでアクセス不可。
- CSRFエラーが発生した場合、SpringSecurityはデバッグでログを出すので要注意。

```
<logger name="org.springframework.security" level="DEBUG">
    <appender-ref ref="STDOUT" />
    <appender-ref ref="APPLICATION_LOG" />
</logger>
```

```
Invalid CSRF token found for http://localhost:8080/hello
```