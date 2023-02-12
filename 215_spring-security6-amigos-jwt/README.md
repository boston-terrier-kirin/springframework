# spring security6 amigos jwt

# POST http://localhost:8080/api/auth/register

- AuthService.register で BCript を使って insert している。
- insert 後、JwtService.generateToken で token をレスポンスとして返している。

# GET http://localhost:8080/api/auth/authenticate

- AuthenticationManager.authenticate が裏で、UserDetailsService を呼び出して認証してくれる。
- 認証後、JwtService.generateToken で token をレスポンスとして返している。

# GET http://localhost:8080/api/test

- JwtAuthenticationFilter で JWT のチェックのみ実施している。
- OncePerRequestFilter を使っているので、UsernamePasswordAuthenticationFilter とは違って、毎リクエスト JWT チェックを動かせる。

# 良いところ

## 方式 1

今回はこっち。こっちを採用すれば、register で jwt をつけられるので、<br>
register したあとに、login しなおす必要がない。

- UsernamePasswordAuthenticationFilter ではなく、Controller 近辺で jwt をつける。
- JwtAuthorizationFilter で jwt をチェック

## 方式 2

この方式は、login のタイミングでしか jwt を返せない。

- UsernamePasswordAuthenticationFilter で jwt をつける。
- JwtAuthorizationFilter で jwt をチェック

# 不明点

- http://localhost:8080/api/test/ だと 403 になってしまう。
