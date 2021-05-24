package com.multi.tenant.tenant;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;
/**
 *  @author: yangchenghuan
 *  @Date: 2021/12/26 9:52
 *  @Description: 多租户工具类
 */
@UtilityClass
public class TenantContextHolder {

    private final ThreadLocal<String> THREAD_LOCAL_TENANT = new TransmittableThreadLocal<>();

    /**
     * 设置租户ID
     * @param tenantId
     */
    public void setTenantId(String tenantId) {
        THREAD_LOCAL_TENANT.set(tenantId);
    }

    /**
     * 获取TTL中的租户ID
     * @return
     */
    public String getTenantId() {
        return THREAD_LOCAL_TENANT.get();
    }

    public void clear() {
        THREAD_LOCAL_TENANT.remove();
    }

}
