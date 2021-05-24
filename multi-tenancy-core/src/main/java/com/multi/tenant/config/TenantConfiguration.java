package com.multi.tenant.config;

import com.multi.tenant.interceptor.TenantFeignInterceptor;
import com.multi.tenant.interceptor.TenantRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * @author: yangchenghuan
 * @Date: 2021/12/26 10:37
 * @Description: 租户信息拦截
 */
public class TenantConfiguration {

    @Bean
    public RequestInterceptor tenantFeignInterceptor() {
        return new TenantFeignInterceptor();
    }

    @Bean
    public ClientHttpRequestInterceptor tenantRequestInterceptor() {
        return new TenantRequestInterceptor();
    }

}
