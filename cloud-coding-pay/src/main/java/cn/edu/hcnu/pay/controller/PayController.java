package cn.edu.hcnu.pay.controller;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.pay.model.PayDTO;
import cn.edu.hcnu.pay.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/24 19:15
 */
@RestController
@RequestMapping("/pay")
@Tag(name = "支付模块")
public class PayController {

    @Autowired
    PayService payService;

    @PostMapping("new")
    @Operation(summary = "创建支付二维码")
    public RestResponse createOrder(@RequestBody PayDTO payDTO) {
        Map map = payService.createQRCode(payDTO);
        return RestResponse.success(map);
    }
}
