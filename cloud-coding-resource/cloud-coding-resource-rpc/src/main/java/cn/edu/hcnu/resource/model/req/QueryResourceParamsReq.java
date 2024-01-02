package cn.edu.hcnu.resource.model.req;

import cn.edu.hcnu.base.model.PageParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件查询请求模型类
 * @date 2022/9/10 8:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "资源查询参数")
public class QueryResourceParamsReq extends PageParams {

    @Schema(description = "媒资文件名称",defaultValue = "")
    private String name;

    @Schema(description = "媒资类型",defaultValue = "001001")
    private String type;

    @Schema(description = "审核状态",defaultValue = "1")
    private String state;
}
