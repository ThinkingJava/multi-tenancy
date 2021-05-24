package com.multi.tenant.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.multi.tenant.dto.SysUserDTO;
import com.multi.tenant.entity.SysUser;
import com.multi.tenant.mapper.SysUserMapper;
import com.multi.tenant.qo.SysUserQO;
import com.multi.tenant.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: yangchenghuan
 * @Date: 2021/12/26 12:06
 * @Description:
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(SysUserQO sysUserQO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserQO, sysUser);
        return save(sysUser);
    }

    @Override
    public Page<SysUserDTO> page(Page page, SysUserQO sysUserQO) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(sysUserQO.getUserName())) {
            queryWrapper.eq(SysUser::getUserName, sysUserQO.getUserName());
        }
        if (StrUtil.isNotBlank(sysUserQO.getPhone())) {
            queryWrapper.eq(SysUser::getPhone, sysUserQO.getPhone());
        }
        Page<SysUser> result = baseMapper.selectPage(page, queryWrapper);
        if (Objects.nonNull(result) && CollUtil.isNotEmpty(result.getRecords())) {
            List<SysUserDTO> list = result.getRecords().stream().map(data -> {
                SysUserDTO sysUserDTO = new SysUserDTO();
                BeanUtils.copyProperties(data, sysUserDTO);
                return sysUserDTO;
            }).collect(Collectors.toList());
            return new Page<SysUserDTO>(result.getCurrent(), result.getSize(), result.getTotal()).setRecords(list);
        }
        return new Page<SysUserDTO>(result.getCurrent(), result.getSize(), result.getTotal()).setRecords(new ArrayList<>());
    }
}
