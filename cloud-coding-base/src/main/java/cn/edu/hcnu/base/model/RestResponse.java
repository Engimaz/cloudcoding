package cn.edu.hcnu.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

/**
 * @author AICHEN
 * @version 1.0
 * @description 通用结果类型
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> implements Serializable {


    private int code;

    /**
     * 响应提示信息
     */
    private String info;

    /**
     * 响应内容
     */
    private T result;


    public RestResponse(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public RestResponse(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.info = resultCode.getInfo();
    }

    /**
     * 错误信息的封装
     *
     * @param <T>
     * @return
     */
    public static <T> RestResponse<T> fail(ResultCode resultCode) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(resultCode.getCode());
        response.setInfo(resultCode.getInfo());
        return response;
    }

    public static <T> RestResponse<T> fail(T result, ResultCode resultCode) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(resultCode.getCode());
        response.setResult(result);
        response.setInfo(response.getInfo());
        return response;
    }


    /**
     * 添加正常响应数据（包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> RestResponse<T> success(ResultCode resultCode) {
        RestResponse<T> response = new RestResponse<>();
        response.setCode(resultCode.getCode());
        response.setInfo(resultCode.getInfo());
        return response;
    }

    public static <T> RestResponse<T> success(T result, String info) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        response.setInfo(info);
        return response;
    }

    public static <T> RestResponse<T> success(ResultCode resultCode, T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        response.setInfo(resultCode.getInfo());
        response.setCode(resultCode.getCode());
        return response;
    }

    /**
     * 添加正常响应数据（不包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> RestResponse<T> success() {
        return new RestResponse<T>();
    }


}