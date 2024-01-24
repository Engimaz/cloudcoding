package cc.cloudcoding.captcha.exception;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import cn.hutool.http.HttpStatus;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/7/17 10:51
 */
@Slf4j
@Component
public class CloudCodingBlockException implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        log.error("【sentinel: BlockException 异常】 {}", e.getRule());
        RestResponse restResponse;
        if (e instanceof FlowException) {
            restResponse = RestResponse.fail(ResultCode.FLOW_LIMITING);
        } else if (e instanceof ParamFlowException) {
            restResponse = RestResponse.fail(ResultCode.PARAM_FLOW_LIMITING);
        } else if (e instanceof DegradeException) {
            restResponse = RestResponse.fail(ResultCode.DEGRADE_LIMITING);
        } else if (e instanceof SystemBlockException) {
            restResponse = RestResponse.fail(ResultCode.SYSTEM_BLOCK);
        } else if (e instanceof AuthorityException) {
            restResponse = RestResponse.fail(ResultCode.AUTHORITY_LIMITING);
        } else {
            restResponse = RestResponse.fail(ResultCode.UN_ERROR);
        }
        httpServletResponse.setStatus(HttpStatus.HTTP_INTERNAL_ERROR);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), restResponse);
    }
}