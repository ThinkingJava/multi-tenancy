package com.multi.tenant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangchenghuan
 * @Date: 2021/12/26 9:50
 * @Description: 多租户配置
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "sys.tenant")
public class TenantConfigProperties {

    /**
     * 维护租户列名称
     */
    private String column = "tenant_id";

    /**
     * 多租户的数据表集合
     */
    private List<String> excludeTables = new ArrayList<>();

}
