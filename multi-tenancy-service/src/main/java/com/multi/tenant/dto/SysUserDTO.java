package com.multi.tenant.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDTO {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;


    /**
     * 0-正常，1-删除
     */
    @TableLogic
    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;


    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

}
