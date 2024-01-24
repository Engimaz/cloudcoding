package cc.cloudcoding.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 14:52
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureUrlDTO {
    private Long id;
    private Long featureId;
    private Long urlId;
}
