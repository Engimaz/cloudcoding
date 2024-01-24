package cc.cloudcoding.pay.service;

import cc.cloudcoding.pay.model.PayDTO;

import java.util.Map;

public interface PayService {
    Map createQRCode(PayDTO payDTO);
}
