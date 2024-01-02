package cn.edu.hcnu.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/25 12:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRes<T,Q>{
    private List<T> list;
    private Long count;
    private Q commonQuery;
}
