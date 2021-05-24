package com.multi.tenant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.multi.tenant.qo.SysUserQO;
import com.multi.tenant.service.SysUserService;
import com.multi.tenant.util.Result;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yangchenghuan
 * @Date: 2021/12/26 12:03
 * @Description:
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理模块")
public class SysUserController {

    private final SysUserService userService;

    @PostMapping("/save")
    public Result save(@RequestBody SysUserQO sysUserQO) {
        return Result.ok(userService.saveUser(sysUserQO));
    }

    @PostMapping("/page")
    public Result page(@RequestBody SysUserQO sysUserQO, Page page) {
        return Result.ok(userService.page(page, sysUserQO));
    }


}
