# SpringSecurity

# POST http://localhost:8080/users

- UserService.createUser で処理する。
- BCryptPasswordEncoder を使って INSERT しているだけで、JWT はつかない。

# POST http://localhost:8080/users/login

- AuthenticationFilter で処理する。
- AuthenticationFilter は UsernamePasswordAuthenticationFilter を継承している。
- /users/login は WebSecurity で定義している。
- UsernamePasswordAuthenticationFilter を継承しているので、/users/login にアクセスすると認証が走る仕組みのはず。
- AuthenticationFilter で JWT をつけている。

# AuthorizationFilter

- Token を確認して、UsernamePasswordAuthenticationToken を SecurityContextHolder にセットしている。
- BasicAuthenticationFilter を継承しているのがちょっとよく分からないところ。

# コンパイル

```
mvn install
```

# 起動

```
mvn spring-boot:run
```