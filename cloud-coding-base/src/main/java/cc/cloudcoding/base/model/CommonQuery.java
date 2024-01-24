package cc.cloudcoding.base.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/25 12:28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonQuery {
    @Schema(name = "page", description = "当前页码",defaultValue = "1")
    @NotNull(message = "页码不能为空")
    @Min(value = 1,message = "页码必须大于等于1")
    private Integer page;

    @Schema(name="size",description = "页面大小",defaultValue = "10")
    @NotNull(message = "页面大小不能为空")
    @Min(value = 1,message = "页面大小必须大于等于1")
    private Integer size;

    @Schema(name = "keyword",description = "搜索关键字")
    private String keyword;
}
