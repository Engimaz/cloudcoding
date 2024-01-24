package cc.cloudcoding.manager.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeatureOrganizationCommand {
    private Long id;

    private Long organizationId;

    private Long featureId;
}
