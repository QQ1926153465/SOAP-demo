package com.example.soapdemo;

import lombok.AllArgsConstructor;

/**
 * @author llk
 * @date 2019-10-04 23:04
 */
@AllArgsConstructor
public enum Code implements BaseCode {

    SUCCESS(200, "成功"),

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "无权限访问"),
    NOT_FOUND(404, "找不到对应请求"),
    METHOD_NOT_ALLOWED(405, "不支持的请求方法"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),
    ERROR(500, "系统繁忙"),
    ARG_ERROR(203,"参数错误"),
    FEIGN_CALL_ERROR(517, "服务间调用出错");


    private final int code;

    private final String msg;

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
