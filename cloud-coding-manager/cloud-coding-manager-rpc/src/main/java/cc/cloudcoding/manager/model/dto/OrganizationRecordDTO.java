package cc.cloudcoding.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRecordDTO {
    private String id;
    private String userId;
    private String organizationId;
    private String content;
}
