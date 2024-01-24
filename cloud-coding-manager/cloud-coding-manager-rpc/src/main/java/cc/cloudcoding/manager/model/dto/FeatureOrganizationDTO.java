package cc.cloudcoding.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureOrganizationDTO {
    private Long id;

    private Long organizationId;

    private Long featureId;
}
