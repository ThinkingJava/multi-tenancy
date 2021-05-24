package com.multi.tenant.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.multi.tenant.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {
    @Autowired
    private SnowFlake snowFlake;

    @Override
    public Long nextId(Object entity) {
        //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
        String bizKey = entity.getClass().getName();
        log.info("bizKey:{}", bizKey);
        long id = snowFlake.nextId();
        return id;
    }

}
