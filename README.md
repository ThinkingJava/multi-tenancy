# 多租户demo

## 1  前言

​	支持多租户模式

## 2 设计

​	mybatis多租户测试

### 2.1 技术选型

mybatis多租户测试

### 2.2 模块划分

将项目划分为以下几个模块

| 名称             | 描述                                                     |
| ---------------- | -------------------------------------------------------- |
| multi-tenancy-core    | 公共核心组件                         |
| multi-tenancy-service    | 测试应用功能模块                                         |

## 3 测试说明
### 3.1 添加测试表

- 创建数据库表用于保存路由配置

  ```sql
   CREATE TABLE `sys_user`  (
  `id` bigint(30) NOT NULL COMMENT '主键ID',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pass_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `tenant_id` int(11) NOT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

 ```
  
- 多租户核心注入bean

  ```java
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 多租户支持
        TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
        tenantLineInnerInterceptor.setTenantLineHandler(tenantHandler());
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
        return interceptor;
    }

  ```
  
- 多租户核心操作工具 TenantContext
  
  ```java
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
  ```









