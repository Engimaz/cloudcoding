package cn.edu.hcnu.manager.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureRes {
    private String id;
    private String name;
    private String status;
    private List<FeatureUrlRes> relations;

}
