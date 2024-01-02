package cn.edu.hcnu.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/25 12:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryUrlReq  {
    /* 分页 */
    private Integer page;
    /* 数量 */
    private Integer size;
    /* 关键字 */
    private String keyword;
}
