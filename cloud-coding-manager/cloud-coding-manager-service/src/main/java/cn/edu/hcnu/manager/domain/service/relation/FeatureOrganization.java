package cn.edu.hcnu.manager.domain.service.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class FeatureOrganization {
    private Long id;
    private Long organizationId;
    private Long featureId;

}
