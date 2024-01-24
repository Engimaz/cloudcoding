package cc.cloudcoding.manager.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 15:20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureUrlRes {
    private String id;
    private String featureId;
    private String urlId;

}
