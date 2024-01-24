package cc.cloudcoding.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRecordReq {

    private String id;
    private String userId;
    private String organizationId;
    private String content;
}
