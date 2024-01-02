package cn.edu.hcnu.pay.service.impl;

import cn.edu.hcnu.id.domain.IDGenerator;
import cn.edu.hcnu.pay.config.AliPayConfig;
import cn.edu.hcnu.pay.model.PayDTO;
import cn.edu.hcnu.pay.service.PayService;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/24 19:21
 */
@Service
@Slf4j
public class AliyunPayService implements PayService {

    @Autowired
    AliPayConfig aliPayConfig;

    @DubboReference(group = "object")
    IDGenerator idGenerator;

    @Override
    public Map createQRCode(PayDTO payDTO) {
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getGateway(), aliPayConfig.getAppid(), aliPayConfig.getPrivate_key(), "json", aliPayConfig.getCharset(), aliPayConfig.getAli_public_key(), aliPayConfig.getSignType());
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        if (aliPayConfig.getNotify_url() != null && aliPayConfig.getNotify_url().length() > 0) {
            request.setNotifyUrl(aliPayConfig.getNotify_url());
        }
        String id = idGenerator.nextID();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", id);
        bizContent.put("total_amount", payDTO.getMoney());
        bizContent.put("subject", "测试商品");

        // todo 创建保存订单


        request.setBizContent(bizContent.toString());
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            System.out.println(response.getQrCode());
            HashMap<String, String> map = new HashMap<>();
            map.put("pid", String.valueOf(id));
            map.put("qrcode", response.getQrCode());
            System.out.println("调用成功");
            return map;
        } else {
            System.out.println("调用失败");
            return null;
        }
    }
}
