# au-api-spring-boot
[![Maven Central](https://img.shields.io/maven-central/v/com.lazycece.au/au-api-spring-boot-starter)](https://search.maven.org/search?q=au-api-spring-boot-starter)
[![License](https://img.shields.io/badge/license-Apache--2.0-green)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://github.com/lazycece/au-api-spring-boot/releases)

Spring boot project for [Au api](https://github.com/lazycece/au-api).

## Quick Start

Complete example can view [au-api-spring-boot-sample](https://github.com/lazycece/au-api-spring-boot/tree/master/au-api-spring-boot-sample)

### Maven dependency

```xml
    <!-- add sonatype repository when use SNAPSHOT version-->
    <repositories>
        <repository>
            <id>sonatype</id>
            <name>sonatype</name>
            <url>https://oss.sonatype.org/content/groups/public</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <releases>
                <enabled>true</enabled>
            </releases>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>com.lazycece.au</groupId>
            <artifactId>au-api-spring-boot-starter</artifactId>
            <version>${au.api.spring.boot.version}</version>
        </dependency>
    </dependencies>
```

### Enable au api

Add the necessary configuration, as follow:

```yaml
au:
  enable: true
  api:
    token:
      enable: true
      secret: secret-key
    param:
      enable: true
      secret: secret-key
```

Implements `TokenHandler` and `ParamsHandler`, as follow:

```java
@Configuration
public class AuConfig {

    @Bean
    public TokenHandler tokenHandler() {
        return new TokenHandler() {
            @Override
            public String noToken() {
                // deal response, and return.
            }

            @Override
            public String invalidToken() {
                // deal response, and return.
            }
        };
    }

    @Bean
    public ParamsHandler paramsHandler() {
        return new ParamsHandler() {
            @Override
            public String validateParamsFail() {
                // deal response, and return.
            }

            @Override
            public String validateTimeFail() {
                // deal response, and return.
            }

            @Override
            public String validateSignFail() {
                // deal response, and return.
            }

            @Override
            public String getWaitEncodeData(String responseBody) {
                // deal response, and return.
            }

            @Override
            public String getResponseBody(String responseBody, String encodeData, String salt) {
                // deal response, and return.
            }
        };
    }
}
``` 

## License

[Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0.html)
 

