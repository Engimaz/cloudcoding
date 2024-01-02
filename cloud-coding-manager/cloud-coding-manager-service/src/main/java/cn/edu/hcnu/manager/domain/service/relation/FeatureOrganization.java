package cn.edu.hcnu.manager.domain.service.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureOrganization {
    private Long id;
    private Long organizationId;
    private Long featureId;

}
