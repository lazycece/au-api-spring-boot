package com.lazycece.au.api.spring.boot.autoconfigure;

import com.lazycece.au.api.params.ApiParams;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * @author lazycece
 */
@ConfigurationProperties(prefix = "au.api")
public class AuApiProperties {

    private AuApiToken token = new AuApiToken();

    private AuApiParam param = new AuApiParam();

    public AuApiToken getToken() {
        return token;
    }

    public void setToken(AuApiToken token) {
        this.token = token;
    }

    public AuApiParam getParam() {
        return param;
    }

    public void setParam(AuApiParam param) {
        this.param = param;
    }

    public static class AuApiToken {
        /**
         * enable token authentication or not.
         */
        private boolean enable = false;
        /**
         * the header name for setting token in http header.
         */
        private String header = "TOKEN-HEADER";
        /**
         * the issuer for token.
         */
        private String issuer = "TOKEN-ISSUER";
        /**
         * the expire time of token.
         */
        private Duration expire = Duration.ofMillis(1800000L);
        /**
         * refresh token or not while finish one request.
         */
        private boolean refresh = true;
        /**
         * the path patterns which should be intercept.
         */
        private List<String> includePatterns = Collections.singletonList("/**");
        /**
         * the path patterns which should not be intercept.
         */
        private List<String> excludePatterns = Collections.emptyList();
        /**
         * the secret key for generating token.
         */
        private String secret;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public Duration getExpire() {
            return expire;
        }

        public void setExpire(Duration expire) {
            this.expire = expire;
        }

        public boolean isRefresh() {
            return refresh;
        }

        public void setRefresh(boolean refresh) {
            this.refresh = refresh;
        }

        public List<String> getIncludePatterns() {
            return includePatterns;
        }

        public void setIncludePatterns(List<String> includePatterns) {
            this.includePatterns = includePatterns;
        }

        public List<String> getExcludePatterns() {
            return excludePatterns;
        }

        public void setExcludePatterns(List<String> excludePatterns) {
            this.excludePatterns = excludePatterns;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public static class AuApiParam {
        /**
         * enable parameter verification or not.
         */
        private boolean enable = false;
        /**
         * the algorithm for encrypting and decrypting parameters.
         */
        private CryptoType cryptoType = CryptoType.DES3;
        /**
         * class type of api parameters.
         */
        private Class<? extends ApiParams> paramClazz = ApiParams.class;
        /**
         * the interval time for validating one request is valid or not.
         */
        private Duration timeInterval = Duration.ofMillis(3000L);
        /**
         * the path patterns which should be intercept.
         */
        private List<String> includePatterns = Collections.singletonList("/**");
        /**
         * the path patterns which should not be intercept.
         */
        private List<String> excludePatterns = Collections.emptyList();
        /**
         * the secret key for generating signature, encrypting parameters, and decrypting parameters.
         */
        private String secret;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public CryptoType getCryptoType() {
            return cryptoType;
        }

        public void setCryptoType(CryptoType cryptoType) {
            this.cryptoType = cryptoType;
        }

        public Class<? extends ApiParams> getParamClazz() {
            return paramClazz;
        }

        public void setParamClazz(Class<? extends ApiParams> paramClazz) {
            this.paramClazz = paramClazz;
        }

        public Duration getTimeInterval() {
            return timeInterval;
        }

        public void setTimeInterval(Duration timeInterval) {
            this.timeInterval = timeInterval;
        }

        public List<String> getIncludePatterns() {
            return includePatterns;
        }

        public void setIncludePatterns(List<String> includePatterns) {
            this.includePatterns = includePatterns;
        }

        public List<String> getExcludePatterns() {
            return excludePatterns;
        }

        public void setExcludePatterns(List<String> excludePatterns) {
            this.excludePatterns = excludePatterns;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
