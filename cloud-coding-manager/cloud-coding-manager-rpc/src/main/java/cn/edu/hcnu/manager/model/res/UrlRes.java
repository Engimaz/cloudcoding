package cn.edu.hcnu.manager.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/2 23:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlRes {
    private String id;
    private String name;
    private String value;
    private String status;
    private String scope;
    private String description;
}
