package com.lazycece.au.api.spring.boot.autoconfigure;

import com.lazycece.au.AuManager;
import com.lazycece.au.AuServletFilter;
import com.lazycece.au.api.params.ApiParams;
import com.lazycece.au.api.params.ParamsHandler;
import com.lazycece.au.api.params.ParamsHolder;
import com.lazycece.au.api.token.Subject;
import com.lazycece.au.api.token.TokenHandler;
import com.lazycece.au.api.token.TokenHolder;
import com.lazycece.au.api.token.serialize.Serializer;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Disable ${@link org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication } before run test.
 *
 * @author lazycece
 */
public class AuApiAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(AuApiAutoConfiguration.class));

    @Test
    public void testEnableAu() {
        this.contextRunner
//                .withPropertyValues("au.enable=true")
                .withUserConfiguration(TestEnableAu.class)
                .run(context -> assertThat(context).hasNotFailed());
    }

    @Test
    public void testApiTokenHasNoSecret() {
        this.contextRunner
                .withPropertyValues("au.api.token.enable=true")
                .withUserConfiguration(TestTokenHandlerConfiguration.class)
                .run(context -> assertThat(context).getFailure().hasMessageContaining("`au.api.token.secret` is null"));
    }

    @Test
    public void testApiTokenHasNoSecretAndNoTokenHandler() {
        this.contextRunner
                .withPropertyValues("au.api.token.enable=true")
                .run(context -> assertThat(context).hasNotFailed());
    }


    @Test
    public void testEnableAuApiToken() {
        this.contextRunner
                .withPropertyValues(
                        "au.api.token.enable=true",
                        "au.api.token.secret=abc")
                .withUserConfiguration(TestTokenHandlerConfiguration.class)
                .run(context -> assertThat(context).hasSingleBean(TokenHolder.class));
    }

    @Test
    public void testCustomSubjectSerializer() {
        this.contextRunner
                .withPropertyValues(
                        "au.api.token.enable=true",
                        "au.api.token.secret=abc")
                .withUserConfiguration(
                        TestTokenHandlerConfiguration.class,
                        TestSubjectSerializerConfiguration.class)
                .run(context -> assertThat(context).hasSingleBean(TokenHolder.class));
    }

    @Test
    public void testConfigAuApiToken() {
        this.contextRunner
                .withPropertyValues(
                        "au.api.token.enable=true",
                        "au.api.token.header=header",
                        "au.api.token.issuer=issuer",
                        "au.api.token.expire=60m",
                        "au.api.token.refresh=false",
                        "au.api.token.include-patterns=/a/**,/b/**",
                        "au.api.token.exclude-patterns=/a/m,/b/m",
                        "au.api.token.secret=abc")
                .withUserConfiguration(TestTokenHandlerConfiguration.class)
                .run(context -> assertThat(context).hasSingleBean(TokenHolder.class));
    }

    @Test
    public void testApiParamHasNoSecret() {
        this.contextRunner
                .withPropertyValues("au.api.param.enable=true")
                .withUserConfiguration(TestParamHandlerConfiguration.class)
                .run(context -> assertThat(context).getFailure().hasMessageContaining("`au.api.param.secret` is null"));
    }

    @Test
    public void testApiParamHasNoSecretAndNoParamHandler() {
        this.contextRunner
                .withPropertyValues("au.api.param.enable=true")
                .run(context -> assertThat(context).hasNotFailed());
    }

    @Test
    public void testEnableAuApiParam() {
        this.contextRunner
                .withPropertyValues("au.api.param.enable=true")
                .withPropertyValues("au.api.param.secret=abc")
                .withUserConfiguration(TestParamHandlerConfiguration.class)
                .run(context -> assertThat(context).hasSingleBean(ParamsHolder.class));
    }

    @Test
    public void testConfigAuApiParam() {
        this.contextRunner
                .withPropertyValues(
                        "au.api.param.enable=true",
                        "au.api.param.crypto-type=aes",
                        "au.api.param.param-clazz=com.lazycece.au.api.spring.boot.autoconfigure.AuApiAutoConfigurationTest.CustomApiParam",
                        "au.api.param.time-interval=6000ms",
                        "au.api.param.include-patterns=/a/**,/b/**",
                        "au.api.param.exclude-patterns=/a/m,/b/m",
                        "au.api.param.secret=abc")
                .withUserConfiguration(TestParamHandlerConfiguration.class)
                .run(context -> assertThat(context).hasSingleBean(ParamsHolder.class));
    }

    @Configuration
    static class TestEnableAu {
        @Bean
        public FilterRegistrationBean<AuServletFilter> filterRegistrationBean() {
            FilterRegistrationBean<AuServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
            filterRegistrationBean.setFilter(new AuServletFilter());
            filterRegistrationBean.setEnabled(true);
            filterRegistrationBean.setOrder(1);
            filterRegistrationBean.addUrlPatterns("/*");
            AuManager.getInstance().setWrapper(true);
            return filterRegistrationBean;
        }
    }

    static class CustomApiParam extends ApiParams {
        private String version;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public boolean validate() {
            return super.validate() && StringUtils.isNotBlank(version);
        }
    }


    @Configuration
    static class TestSubjectSerializerConfiguration {

        @Bean
        Serializer subjectSerializer() {
            return new Serializer() {
                @Override
                public String serialize(Subject subject) throws Exception {
                    return null;
                }

                @Override
                public Subject deserialize(String s) throws Exception {
                    return null;
                }
            };
        }
    }

    @Configuration
    static class TestTokenHandlerConfiguration {

        @Bean
        public TokenHandler tokenHandler() {
            return new TokenHandler() {
                @Override
                public String noToken() {
                    return null;
                }

                @Override
                public String invalidToken() {
                    return null;
                }
            };
        }
    }

    @Configuration
    static class TestParamHandlerConfiguration {

        @Bean
        public ParamsHandler paramsHandler() {
            return new ParamsHandler() {
                @Override
                public String validateParamsFail() {
                    return null;
                }

                @Override
                public String validateTimeFail() {
                    return null;
                }

                @Override
                public String validateSignFail() {
                    return null;
                }

                @Override
                public String getWaitEncodeData(String s) {
                    return null;
                }

                @Override
                public String getResponseBody(String s, String s1, String s2) {
                    return null;
                }
            };
        }
    }
}
