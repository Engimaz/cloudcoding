package cn.edu.hcnu.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/5/10 22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    // 数据列表
    private List<T> list;
    //总记录数
    private long counts;
    //当前页码
    private long page;
    //每页记录数
    private long size;
}
