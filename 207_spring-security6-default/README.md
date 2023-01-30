# Spring Security 6

今までは WebSecurityConfigurerAdapter を継承していたが、Spring Security6 では SecurityFilterChain を@Bean 定義するようになったらしい。

```
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)http.authorizeHttpRequests().anyRequest()).authenticated();
        http.formLogin();
        http.httpBasic();
        return (SecurityFilterChain)http.build();
    }
}
```
