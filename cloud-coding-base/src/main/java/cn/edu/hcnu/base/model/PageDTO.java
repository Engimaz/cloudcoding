package cn.edu.hcnu.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/25 12:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T,Q>  implements Serializable {
    private List<T> list;
    private Long count;
    private Q commonQuery;
}
