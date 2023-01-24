# Actuator
application.propertiesに、management.endpoints.web.exposure.include=* を設定する。<br>
management.endpoints.web.exposure.include=metrics にすれば、metricsだけをGETできる。

```
management.endpoints.web.exposure.include=metrics
```

- http://localhost:8080/actuator
- http://localhost:8080/actuator/metrics
- http://localhost:8080/actuator/metrics/jvm.memory.max

# profile
application-dev.properties, application-prd.properties を用意して、application.propertiesにprofileを定義する。

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