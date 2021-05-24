package com.multi.tenant.filter;

import cn.hutool.core.util.StrUtil;
import com.multi.tenant.constants.BaseConstants;
import com.multi.tenant.exception.TenantContextException;
import com.multi.tenant.tenant.TenantContextHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  @author: yangchenghuan
 *  @Date: 2021/12/26 10:21
 *  @Description: 租户过滤
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextFilter extends GenericFilterBean {

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String headerTenantId = request.getHeader(BaseConstants.TENANT_ID);
        String paramTenantId = request.getParameter(BaseConstants.TENANT_ID);

        log.debug("获取header中的租户ID为:{}", headerTenantId);

        if (StrUtil.isNotBlank(headerTenantId)) {
            TenantContextHolder.setTenantId(headerTenantId);
        }
        else if (StrUtil.isNotBlank(paramTenantId)) {
            TenantContextHolder.setTenantId(paramTenantId);
        }
        else {
           throw new TenantContextException("获取租户ID失败！");
        }

        filterChain.doFilter(request, response);
        TenantContextHolder.clear();
    }

}
