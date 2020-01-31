package com.lazycece.au.api.spring.boot.autoconfigure;

import com.lazycece.au.api.token.serialize.Serializer;
import com.lazycece.au.api.token.serialize.SubjectSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lazycece
 */
@Configuration
public class SubjectSerializerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Serializer serializer() {
        System.out.println("=============== test serializer  ");
        // TODO: 2020/1/31 validate and clean
        // TODO: 2020/1/31 use json
        return new SubjectSerializer();
    }
}
