package com.multi.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.multi.tenant.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
