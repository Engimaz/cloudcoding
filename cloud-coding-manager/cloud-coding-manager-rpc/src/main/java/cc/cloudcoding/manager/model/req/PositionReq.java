package cc.cloudcoding.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/4 19:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionReq {
    private String id;
    private String name;
    private String status;
    private String value;
}
