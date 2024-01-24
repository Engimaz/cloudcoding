package cc.cloudcoding.captcha.exception;

import cc.cloudcoding.base.model.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/6 1:38
 */
@RestControllerAdvice
@Slf4j
public class ValidationExceptionHandler {
    /**
     * 方法参数无效异常
     *
     * @param e e
     * @return {@link RestResponse}
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> msgList = new ArrayList<>();
        //将错误信息放在 msgList
        bindingResult.getFieldErrors().forEach(item -> msgList.add(item.getDefaultMessage()));
        //拼接错误信息
        String msg = StringUtils.join(msgList, ",");
        log.error("【系统异常】{}", msg);
        return new RestResponse(400, msg);
    }
}
