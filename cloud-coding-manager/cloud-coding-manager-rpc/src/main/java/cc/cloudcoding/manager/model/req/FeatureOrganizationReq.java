package cc.cloudcoding.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/23 1:01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureOrganizationReq {
    private String id;

    private String organizationId;

    private String featureId;
}
