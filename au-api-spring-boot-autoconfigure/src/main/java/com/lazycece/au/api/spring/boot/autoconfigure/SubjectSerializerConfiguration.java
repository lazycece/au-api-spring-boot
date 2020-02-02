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
        return new SubjectSerializer();
    }
}
