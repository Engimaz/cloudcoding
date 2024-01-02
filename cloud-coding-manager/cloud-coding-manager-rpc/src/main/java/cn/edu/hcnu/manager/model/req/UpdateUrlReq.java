package cn.edu.hcnu.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/2 23:35
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUrlReq {
    private Long id;
    private String name;
    private String value;
    private String status;
    private String scope;


}
