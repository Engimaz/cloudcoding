package cn.edu.hcnu.manager.domain.service.feature;

import cn.edu.hcnu.manager.domain.service.relation.FeatureUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 功能领域
 * @author: Administrator
 * @time: 2023/9/12 13:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feature {
    private Long id;
    private String name;
    private String status;
    private List<FeatureUrl> relations;
}
