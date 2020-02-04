package com.lazycece.au.api.spring.boot.autoconfigure;

import com.lazycece.au.AuManager;
import com.lazycece.au.AuServletFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lazycece
 */
@Configuration
public class AuConfiguration {

    private final AuProperties auProperties;

    @Autowired
    public AuConfiguration(AuProperties auProperties) {
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
}
