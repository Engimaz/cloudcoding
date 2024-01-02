package cn.edu.hcnu.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/5/10 22:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PageParams {
    /**
     * 页码
     */
    @Schema(description = "页码", defaultValue = "1")
    private Integer page;
    /**
     * 每页显示记录数
     */
    @Schema(description = "每页记录数", defaultValue = "10")
    private Integer size;

    @Schema(description = "查询关键字")
    private String keyword;

}
