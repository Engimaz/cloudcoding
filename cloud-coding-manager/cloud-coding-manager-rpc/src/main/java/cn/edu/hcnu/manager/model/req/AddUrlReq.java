package cn.edu.hcnu.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/2 23:34
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddUrlReq  {
    private String name;
    private String value;
    private String status;
    private String scope;
    private String description;
}
