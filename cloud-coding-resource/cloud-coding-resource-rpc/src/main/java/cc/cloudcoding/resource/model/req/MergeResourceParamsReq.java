package cc.cloudcoding.resource.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/24 6:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeResourceParamsReq {
    private String md5;
    private String name;
    private String userId;
    private String type;
}
