package cn.edu.hcnu.pay.service;

import cn.edu.hcnu.pay.model.PayDTO;

import java.util.Map;

public interface PayService {
    Map createQRCode(PayDTO payDTO);
}
