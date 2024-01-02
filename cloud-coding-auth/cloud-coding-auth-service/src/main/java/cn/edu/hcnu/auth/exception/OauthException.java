package cn.edu.hcnu.auth.exception;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/11 1:18
 */
@RestControllerAdvice
public class OauthException {

    /**
     * 授权方式不支持异常
     *
     * @param e e
     * @return {@link RestResponse}
     */
    @ResponseBody
    @ExceptionHandler(UnsupportedGrantTypeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResponse customException(UnsupportedGrantTypeException e) {
        return RestResponse.fail(ResultCode.UNSUPPORTED_GRANT_TYPE);
    }

    /**
     * 用户账号异常
     *
     * @param e e
     * @return {@link RestResponse}
     */
    @ResponseBody
    @ExceptionHandler(InvalidGrantException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResponse customException(InvalidGrantException e) {

        String errorDescription = e.getMessage();
        if (errorDescription.contains("Bad credentials")) {
            // 处理密码不正确的情况
            return RestResponse.fail(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        } else if (errorDescription.contains("locked")) {
            // 处理账号被锁定的情况
            return RestResponse.fail(ResultCode.ACCOUNT_LOCK);
        }
        return RestResponse.fail(ResultCode.ERROR);
    }

    /**
     * token 异常
     *
     * @param e e
     * @return {@link RestResponse}
     */
    @ResponseBody
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResponse customException(InvalidTokenException e) {
        return RestResponse.fail(ResultCode.INVALID_TOKEN);
    }



}
