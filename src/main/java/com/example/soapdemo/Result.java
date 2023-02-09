package com.example.soapdemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author llk
 * @date 2019-10-04 19:25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 出错的请求路径
     */
    private String path;

    @JsonIgnore
    private transient static Object defaultData = new Object();

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public boolean isSuccess() {
        return code == Code.SUCCESS.getCode();
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(int code, String msg, T data, String path) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(T data) {
        if (data == null) {
            data = (T) defaultData;
        }
        return new Result<>(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(Code.SUCCESS.getCode(), msg, null);
    }

    public static <T> Result<T> json(T data) {
        return new Result<>(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), data);
    }

    public static Result<Object> success() {
        return success(defaultData);
    }

    public static <T> Result<T> success(BaseCode code) {
        return success(code.getMsg());
    }

    public static <T> Result<T> error(String message) {
        return error(Code.ERROR, message, null);
    }

    public static <T> Result<T> error(BaseCode code) {
        return error(code, null);
    }

    public static <T> Result<T> error(BaseCode code, String path) {
        return new Result<>(code.getCode(), code.getMsg(), null, path);
    }

    public static <T> Result<T> error(BaseCode code, String message, String path) {
        return new Result<>(code.getCode(), message, null, path);
    }

    public static <T> Result<T> error(int code, String message, String path) {
        return new Result<>(code, message, null, path);
    }


}
