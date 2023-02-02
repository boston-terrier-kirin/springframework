# Actuator

application.properties に、management.endpoints.web.exposure.include=\* を設定する。<br>
management.endpoints.web.exposure.include=metrics にすれば、metrics だけを GET できる。

```
management.endpoints.web.exposure.include=metrics
```

- http://localhost:8080/actuator
- http://localhost:8080/actuator/metrics
- http://localhost:8080/actuator/metrics/jvm.memory.max

# Profile

application-dev.properties, application-prd.properties を用意して、application.properties に profile を定義する。

```
spring.profiles.active=prd
```

# ConfigurationProperties

CurrencyServiceConfig.java

```
@ConfigurationProperties(prefix = "currency-service")
@Component
public class CurrencyServiceConfig {
  ...
}
```
