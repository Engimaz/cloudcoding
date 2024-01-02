package cn.edu.hcnu.base.execption;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/5/11 15:28
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 云上编程自定义异常
     *
     * @param e e
     * @return {@link RestResponse}
     */
    @ResponseBody
    @ExceptionHandler(CloudCodingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse customException(CloudCodingException e) {
        log.error("【云上编程系统异常】{}", e.getErrMessage(), e);
        return new RestResponse(400, e.getErrMessage());
    }


    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("【系统异常】{}", e.getMessage(), e);
        return RestResponse.fail(e, ResultCode.PARAM_MISS);
    }

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
        //将错误信息放在 msgList
        List<String> collect = bindingResult.getFieldErrors().stream().map(item -> item.getDefaultMessage()).collect(Collectors.toList());
        //拼接错误信息
        String msg = StringUtils.join(collect, ",");
        log.error("【系统异常】{}", msg);
        return  RestResponse.fail(msg,ResultCode.VALID_FAIL);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse methodArgumentBindingException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        //将错误信息放在 msgList
        List<String> collect = bindingResult.getFieldErrors().stream().map(item -> item.getDefaultMessage()).collect(Collectors.toList());
        //拼接错误信息
        String msg = StringUtils.join(collect, ",");
        log.error("【系统异常】{}", msg);
        return  RestResponse.fail(msg,ResultCode.VALID_FAIL);
    }

    /**
     * 最大异常
     *
     * @param e e
     * @return {@link RestResponse}
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResponse exception(Exception e) {
        log.error("【系统异常】{}", e.getMessage(), e);
        return new RestResponse(ResultCode.UNKOWN_ERROR);
    }


}
