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
@EnableConfigurationProperties({AuProperties.class, AuApiProperties.class})
@Import({SubjectSerializerConfiguration.class})
@AutoConfigureAfter(SubjectSerializerConfiguration.class)
public class AuApiAutoConfiguration {

    // use order(-4,-3,-2) to avoid user define au-filter(default order is -1).
    private final int[] order = new int[]{-4, -3, -2};
    private final AuApiProperties auApiProperties;
    private final AuProperties auProperties;

    @Autowired
    public AuApiAutoConfiguration(AuApiProperties auApiProperties, AuProperties auProperties) {
        this.auApiProperties = auApiProperties;
        this.auProperties = auProperties;
    }

    @Bean
    @ConditionalOnMissingFilterBean
    public FilterRegistrationBean<AuServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuServletFilter());
        filterRegistrationBean.setEnabled(auProperties.isEnable());
        filterRegistrationBean.setOrder(auProperties.getOrder());
        filterRegistrationBean.setUrlPatterns(auProperties.getUrlPatterns());
        AuManager.getInstance().setWrapper(auProperties.isWrapper());
        return filterRegistrationBean;
    }

    @Bean
    @ConditionalOnBean(TokenHandler.class)
    @ConditionalOnProperty(prefix = "au.api.token", name = "enable", havingValue = "true")
    public TokenHolder tokenHolder(Serializer serializer, TokenHandler tokenHandler) {
        AuApiProperties.AuApiToken apiToken = auApiProperties.getToken();
        Assert.hasText(apiToken.getSecret(), "`au.api.token.secret` is null");
        TokenHolder tokenHolder = TokenHolder.build(apiToken.getSecret())
                .serializer(serializer)
                .tokenHeader(apiToken.getHeader())
                .issuer(apiToken.getIssuer())
                .expire(apiToken.getExpire().toMillis())
                .refresh(apiToken.isRefresh());
        AuManager.getInstance().addAuFilter(new AuTokenFilter(tokenHolder, tokenHandler))
                .includePatterns(auApiProperties.getToken().getIncludePatterns())
                .excludePatterns(auApiProperties.getToken().getExcludePatterns())
                .order(order[0]);
        return tokenHolder;
    }

    @Bean
    @ConditionalOnBean(ParamsHandler.class)
    @ConditionalOnProperty(prefix = "au.api.param", name = "enable", havingValue = "true")
    public ParamsHolder paramsHolder(ParamsHandler paramsHandler) {
        AuApiProperties.AuApiParam apiParam = auApiProperties.getParam();
        Assert.hasText(apiParam.getSecret(), "`au.api.param.secret` is null");
        ParamsHolder paramsHolder = ParamsHolder.build(apiParam.getSecret())
                .dataCrypto(getDataCrypto(apiParam.getCryptoType()))
                .timeInterval(apiParam.getTimeInterval().toMillis())
                .paramsClazz(apiParam.getParamClazz());
        AuManager.getInstance().addAuFilter(MultiPartRequestFilter.class)
                .includePatterns(auApiProperties.getParam().getIncludePatterns())
                .excludePatterns(auApiProperties.getParam().getExcludePatterns())
                .order(order[1]);
        AuManager.getInstance().addAuFilter(new AuParamFilter(paramsHolder, paramsHandler))
                .includePatterns(auApiProperties.getParam().getIncludePatterns())
                .excludePatterns(auApiProperties.getParam().getExcludePatterns())
                .order(order[2]);
        return paramsHolder;
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
