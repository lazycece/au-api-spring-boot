package com.lazycece.au.api.spring.boot.autoconfigure;

import com.lazycece.au.AuManager;
import com.lazycece.au.AuServletFilter;
import com.lazycece.au.api.params.ParamsHandler;
import com.lazycece.au.api.params.ParamsHolder;
import com.lazycece.au.api.params.crypt.AESCrypto;
import com.lazycece.au.api.params.crypt.DES3Crypto;
import com.lazycece.au.api.params.crypt.DataCrypto;
import com.lazycece.au.api.params.filter.AuParamFilter;
import com.lazycece.au.api.params.filter.MultiPartRequestFilter;
import com.lazycece.au.api.token.TokenHandler;
import com.lazycece.au.api.token.TokenHolder;
import com.lazycece.au.api.token.filter.AuTokenFilter;
import com.lazycece.au.api.token.serialize.Serializer;
import com.lazycece.au.matcher.PathMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

/**
 * @author lazycece
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(AuApiProperties.class)
@Import(value = {PathMatcherConfiguration.class, SubjectSerializerConfiguration.class})
@AutoConfigureAfter(value = {PathMatcherConfiguration.class, SubjectSerializerConfiguration.class})
public class AuApiAutoconfigure {

    private final AuApiProperties auApiProperties;

    @Autowired
    public AuApiAutoconfigure(AuApiProperties auApiProperties) {
        this.auApiProperties = auApiProperties;
    }

    @Bean
    @ConditionalOnBean(value = {TokenHandler.class, ParamsHandler.class})
    @ConditionalOnMissingFilterBean
    @ConditionalOnProperty(prefix = "au-api", name = "enable", havingValue = "true")
    public FilterRegistrationBean<AuServletFilter> filterRegistrationBean(TokenHolder tokenHolder, ParamsHolder paramsHolder, PathMatcher pathMatcher,
                                                                          TokenHandler tokenHandler, ParamsHandler paramsHandler) {
        System.out.println("=============== test filterRegistrationBean  ");
        // TODO: 2020/1/31 validate and clean
        FilterRegistrationBean<AuServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuServletFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);

        AuManager auManager = AuManager.getInstance();
        auManager.setPathMatcher(pathMatcher);
        if (auApiProperties.getToken().isEnable()) {
            auManager.addAuFilter(new AuTokenFilter(tokenHolder, tokenHandler))
                    .includePatterns(auApiProperties.getToken().getIncludePatterns())
                    .excludePatterns(auApiProperties.getToken().getExcludePatterns())
                    .order(-4);
        }
        if (auApiProperties.getParam().isEnable()) {
            auManager.addAuFilter(MultiPartRequestFilter.class).includePatterns("/**").order(-3);
            auManager.addAuFilter(new AuParamFilter(paramsHolder, paramsHandler))
                    .includePatterns(auApiProperties.getParam().getIncludePatterns())
                    .excludePatterns(auApiProperties.getParam().getExcludePatterns())
                    .order(-2);
        }
        return filterRegistrationBean;
    }

    @Bean
    @ConditionalOnProperty(prefix = "au-api.token", name = "secret")
    public TokenHolder tokenHolder(Serializer serializer) {
        System.out.println("=============== test tokenHolder  ");
        // TODO: 2020/1/31 validate and clean
        AuApiProperties.ApiToken apiToken = auApiProperties.getToken();
        Assert.hasText(apiToken.getSecret(), "`au-api.token.secret` is null");
        return TokenHolder.build(apiToken.getSecret())
                .serializer(serializer)
                .tokenHeader(apiToken.getHeader())
                .issuer(apiToken.getIssuer())
                .expire(apiToken.getExpire().toMillis())
                .refresh(apiToken.isRefresh());
    }

    @Bean
    @ConditionalOnProperty(prefix = "au-api.param", name = "secret")
    public ParamsHolder paramsHolder() {
        System.out.println("=============== test paramsHolder  ");
        // TODO: 2020/1/31 validate and clean
        AuApiProperties.ApiParam apiParam = auApiProperties.getParam();
        Assert.hasText(apiParam.getSecret(), "`au-api.param.secret` is null");
        return ParamsHolder.build(apiParam.getSecret())
                .dataCrypto(getDataCrypto(apiParam.getCryptoType()))
                .timeInterval(apiParam.getTimeInterval().toMillis())
                .paramsClazz(apiParam.getParamClazz());
    }

    private DataCrypto getDataCrypto(CryptoType type) {
        DataCrypto dataCrypto;
        switch (type) {
            case AES:
                dataCrypto = new AESCrypto();
                break;
            default:
                dataCrypto = new DES3Crypto();
        }
        return dataCrypto;
    }
}
