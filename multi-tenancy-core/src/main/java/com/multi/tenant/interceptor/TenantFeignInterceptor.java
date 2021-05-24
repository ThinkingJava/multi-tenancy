package com.multi.tenant.interceptor;

import cn.hutool.core.util.StrUtil;
import com.multi.tenant.constants.BaseConstants;
import com.multi.tenant.tenant.TenantContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yangchenghuan
 * @Date: 2021/12/26 10:36
 * @Description: feign请求头拦截
 */
@Slf4j
public class TenantFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (StrUtil.isBlank(TenantContextHolder.getTenantId())) {
            log.debug("TTL 中的 租户ID为空，feign拦截器 >> 跳过");
            return;
        }
        requestTemplate.header(BaseConstants.TENANT_ID, TenantContextHolder.getTenantId().toString());
    }

}
