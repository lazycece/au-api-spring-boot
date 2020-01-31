package com.lazycece.au.api.spring.boot.autoconfigure;

import com.lazycece.au.matcher.AntPathMatcher;
import com.lazycece.au.matcher.PathMatcher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lazycece
 */
@Configuration
public class PathMatcherConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PathMatcher pathMatcher() {
        System.out.println("=============== test pathMatcher  ");
        // TODO: 2020/1/31 validate and clean
        return new AntPathMatcher();
    }
}
