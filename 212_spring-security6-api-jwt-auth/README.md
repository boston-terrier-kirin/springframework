# SpringSecurity

# POST http://localhost:8080/user/register

- UserService.UserServiceImpl で処理する。
- BCryptPasswordEncoder を使って INSERT しているだけで、JWT はつかない。

# POST http://localhost:8080/authenticate

- AuthenticationFilter で処理する。
- AuthenticationFilter は UsernamePasswordAuthenticationFilter を継承している。
- WebSecurity で /authenticate にアクセスした場合のみ、AuthenticationFilter が動くようにしている。
- AuthenticationFilter で JWT をつけている。
- UserDetailService を使わずに、CustomAuthenticationManager を自分で実装している。

```
ExceptionHandlerFilter
　↓
AuthenticationFilter.attemptAuthentication
　↓
AuthenticationManager.authentication
　↓
AuthenticationFilter.successfulAuthentication / unsuccessfulAuthentication
```

# POST http://localhost:8080/course/all

- Token を確認して、UsernamePasswordAuthenticationToken を SecurityContextHolder にセットしている。
- /authenticate ではないので、AuthenticationFilter は動かない。
- Token なしでアクセスした場合、SecurityContextHolder に Authentication がセットされないのでエラーになる。
- 206_spring-security-example_v3_jwt とは違い、BasicAuthenticationFilter を継承していない。

```
ExceptionHandlerFilter
　↓
JWTAuthorizationFilter
```
