package com.multi.tenant.util;

import com.multi.tenant.constants.BaseConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    @Getter
    @Setter
    @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
    private int code;

    @Getter
    @Setter
    @ApiModelProperty(value = "返回信息")
    private String msg;

    @Getter
    @Setter
    @ApiModelProperty(value = "数据")
    private T data;

    public static <T> Result<T> ok() {
        return restResult(null, BaseConstants.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, BaseConstants.SUCCESS, null);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, BaseConstants.SUCCESS, msg);
    }

    public static <T> Result<T> failed() {
        return restResult(null, BaseConstants.FAIL, null);
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, BaseConstants.FAIL, msg);
    }

    public static <T> Result<T> failed(T data) {
        return restResult(data, BaseConstants.FAIL, null);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return restResult(data, BaseConstants.FAIL, msg);
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


}
