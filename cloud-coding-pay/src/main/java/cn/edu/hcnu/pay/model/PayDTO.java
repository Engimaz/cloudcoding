package cn.edu.hcnu.pay.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/24 19:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "创建支付二维码数据传输对象")
public class PayDTO {
    @Schema(description = "支付金额", defaultValue = "0.01")
    private Double money;
    @Schema(description = "商品id")
    private String id;
}
