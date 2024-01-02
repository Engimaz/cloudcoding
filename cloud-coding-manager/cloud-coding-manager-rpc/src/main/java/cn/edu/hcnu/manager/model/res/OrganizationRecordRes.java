package cn.edu.hcnu.manager.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRecordRes {
    private String id;
    private String userId;
    private String organizationId;
    private String content;
}
