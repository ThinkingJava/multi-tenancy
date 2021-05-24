package com.multi.tenant.tenant;

import com.multi.tenant.exception.TenantContextException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
@UtilityClass
public class TenantContext {

    @FunctionalInterface
    public interface VoidHandleAs<T> {

        /**
         * 执行业务逻辑
         *
         * @param tenantId
         * @throws Exception
         */
        void run(T tenantId) throws Exception;

    }

    @FunctionalInterface
    public interface ReturnHandleAs<T, R> {

        /**
         * 执行业务逻辑,返回一个值
         *
         * @param tenantId
         * @return
         * @throws Exception
         */
        R run(T tenantId) throws Exception;

    }

    /**
     * 以某个租户的身份运行
     *
     * @param tenant 租户ID
     * @param func
     */
    public void voidRunAs(String tenant, VoidHandleAs<String> func) {
        final String pre = TenantContextHolder.getTenantId();
        try {
            log.trace("TenantBroker 切换租户{} -> {}", pre, tenant);
            TenantContextHolder.setTenantId(tenant);
            func.run(tenant);
        } catch (Exception e) {
            throw new TenantContextException(e.getMessage(), e);
        } finally {
            log.trace("TenantBroker 还原租户{} <- {}", pre, tenant);
            TenantContextHolder.setTenantId(pre);
        }
    }

    /**
     * 以某个租户的身份运行
     *
     * @param tenant 租户ID
     * @param func
     * @param <T>    返回数据类型
     * @return
     */
    public <T> T returnRunAs(String tenant, ReturnHandleAs<String, T> func) {
        final String pre = TenantContextHolder.getTenantId();
        try {
            log.trace("TenantBroker 切换租户{} -> {}", pre, tenant);
            TenantContextHolder.setTenantId(tenant);
            return func.run(tenant);
        } catch (Exception e) {
            throw new TenantContextException(e.getMessage(), e);
        } finally {
            log.trace("TenantBroker 还原租户{} <- {}", pre, tenant);
            TenantContextHolder.setTenantId(pre);
        }
    }

    /**
     * 以某个租户的身份运行
     *
     * @param supplier
     * @param func
     */
    public void voidRunAs(Supplier<String> supplier, VoidHandleAs<String> func) {
        voidRunAs(supplier.get(), func);
    }

    /**
     * 以某个租户的身份运行
     *
     * @param supplier
     * @param func
     * @param <T>      返回数据类型
     * @return
     */
    public <T> T returnRunAs(Supplier<String> supplier, ReturnHandleAs<String, T> func) {
        return returnRunAs(supplier.get(), func);
    }


}
