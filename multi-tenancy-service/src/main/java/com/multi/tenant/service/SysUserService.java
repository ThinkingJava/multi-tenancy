package com.multi.tenant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.multi.tenant.dto.SysUserDTO;
import com.multi.tenant.entity.SysUser;
import com.multi.tenant.qo.SysUserQO;

public interface SysUserService extends IService<SysUser> {

    /**
     * 保存用户信息
     */
    Boolean saveUser(SysUserQO sysUserQO);

    /**
     * 分页查询
     */
    Page<SysUserDTO> page(Page page, SysUserQO sysUserQO);

}
