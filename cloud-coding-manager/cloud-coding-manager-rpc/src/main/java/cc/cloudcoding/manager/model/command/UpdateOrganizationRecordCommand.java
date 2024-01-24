package cc.cloudcoding.manager.model.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationRecordCommand {
    private String id;
    private String userId;
    private String organizationId;
    private String content;
}
