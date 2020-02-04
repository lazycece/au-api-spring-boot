package com.lazycece.au.api.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * @author lazycece
 */
@ConfigurationProperties(prefix = "au")
public class AuProperties {
    /**
     * enable au or not
     */
    private boolean enable = true;
    /**
     * servlet order
     */
    private int order = 1;
    /**
     * servlet url patterns
     */
    private List<String> urlPatterns = Collections.singletonList("/*");
    /**
     * wrapper request and response or not.
     */
    private boolean wrapper = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public boolean isWrapper() {
        return wrapper;
    }

    public void setWrapper(boolean wrapper) {
        this.wrapper = wrapper;
    }
}
