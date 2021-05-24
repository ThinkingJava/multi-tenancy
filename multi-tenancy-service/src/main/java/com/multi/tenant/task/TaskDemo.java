package com.multi.tenant.task;

import com.multi.tenant.tenant.TenantContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskDemo {

    @Scheduled
    public void task() {
        String tenantId = "1101";
        TenantContext.voidRunAs(tenantId,(id) -> {handle(); });
    }

    public void handle() {

    }

}
