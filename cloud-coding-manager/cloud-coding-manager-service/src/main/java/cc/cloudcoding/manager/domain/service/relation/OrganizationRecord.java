package cc.cloudcoding.manager.domain.service.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRecord {
    private String id;
    private String userId;
    private String organizationId;
    private String content;
}
