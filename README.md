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
    public void initRoute() {
        redisTemplate.delete(CacheConstants.ROUTE_KEY);
          log.info("开始初始化网关路由");

          gatewayRouteConfigService.list().forEach(route -> {
              RouteDefinitionVo vo = new RouteDefinitionVo();
              vo.setRouteName(route.getRouteName());
              vo.setId(route.getRouteId());
              vo.setUri(URI.create(route.getUri()));
              vo.setOrder(route.getOrder());
  
              JSONArray filterObj = JSONUtil.parseArray(route.getFilters());
              vo.setFilters(filterObj.toList(FilterDefinition.class));
              JSONArray predicateObj = JSONUtil.parseArray(route.getPredicates());
              vo.setPredicates(predicateObj.toList(PredicateDefinition.class));
  
              log.info("加载路由ID：{},{}", route.getRouteId(), vo);
              redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinitionVo.class));
              redisTemplate.opsForHash().put(CacheConstants.ROUTE_KEY, route.getRouteId(), vo);
          });
  
          // 通知网关重置路由
          redisTemplate.convertAndSend(CacheConstants.ROUTE_JVM_RELOAD_TOPIC, "路由信息,网关缓存更新");
          log.debug("初始化网关路由结束 ");
      }
  ```








