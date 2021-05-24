package com.multi.tenant.interceptor;

import com.multi.tenant.constants.BaseConstants;
import com.multi.tenant.tenant.TenantContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 *  @author: yangchenghuan
 *  @Date: 2021/12/26 10:29
 *  @Description: 传递 RestTemplate 请求的租户ID
 */ 
public class TenantRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        if (TenantContextHolder.getTenantId() != null) {
            request.getHeaders().set(BaseConstants.TENANT_ID, String.valueOf(TenantContextHolder.getTenantId()));
        }

        return execution.execute(request, body);
    }

}
