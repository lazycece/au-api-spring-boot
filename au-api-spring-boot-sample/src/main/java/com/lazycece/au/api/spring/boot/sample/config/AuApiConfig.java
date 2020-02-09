package com.lazycece.au.api.spring.boot.sample.config;

import com.lazycece.au.api.params.ParamsHandler;
import com.lazycece.au.api.params.utils.JsonUtils;
import com.lazycece.au.api.spring.boot.sample.response.ResCode;
import com.lazycece.au.api.spring.boot.sample.response.ResponseMap;
import com.lazycece.au.api.token.TokenHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lazycece
 */
@Configuration
public class AuApiConfig {

    @Bean
    public TokenHandler tokenHandler() {
        return new TokenHandler() {
            @Override
            public String noToken() {
                return JsonUtils.toJSONString(ResponseMap.fail(ResCode.INVALID_TOKEN, "token is null"));
            }

            @Override
            public String invalidToken() {
                return JsonUtils.toJSONString(ResponseMap.fail(ResCode.INVALID_TOKEN, "invalid token"));
            }
        };
    }

    @Bean
    public ParamsHandler paramsHandler() {
        return new ParamsHandler() {
            @Override
            public String validateParamsFail() {
                return JsonUtils.toJSONString(ResponseMap.fail("validate param fail"));
            }

            @Override
            public String validateTimeFail() {
                return JsonUtils.toJSONString(ResponseMap.fail("invalid request"));
            }

            @Override
            public String validateSignFail() {
                return JsonUtils.toJSONString(ResponseMap.fail("validate sign fail"));
            }

            @Override
            public String getWaitEncodeData(String responseBody) {
                ResponseMap responseMap = JsonUtils.parseObject(responseBody, ResponseMap.class);
                return JsonUtils.toJSONString(responseMap.get(ResponseMap.DATA_FIELD));
            }

            @Override
            public String getResponseBody(String responseBody, String encodeData, String salt) {
                ResponseMap responseMap = JsonUtils.parseObject(responseBody, ResponseMap.class);
                responseMap.putting(ResponseMap.DATA_FIELD, encodeData)
                        .putting(ResponseMap.SALT_FIELD, salt);
                return JsonUtils.toJSONString(responseMap);
            }
        };
    }
}
