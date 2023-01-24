# Keycloak OAuth2

# web-client

- http://localhost:8087
- OAuth2 クライアントになっている。
- http://localhost:8087 は認証なしで、http://localhost:8087/users に遷移するタイミングで Keycloak の認証が必要になる。

# api-gateway

- http://localhost:8082
- api-gateway として、8081 にリダイレクトする。

# resource-server

- http://localhost:8081

# web-client-02

- http://localhost:8088
- web-client と同じく、OAuth2 クライアントになっている。
- web-client 側でログインすれば、web-client-02 は SSO が効く。

# pkce-client

- http://localhost:8181
- web-client と同じく、OAuth2 クライアントになっている。
- SPA を模して、PKCE をやっている。
