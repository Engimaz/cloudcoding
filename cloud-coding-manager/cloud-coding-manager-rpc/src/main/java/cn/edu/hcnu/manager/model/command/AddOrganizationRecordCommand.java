package cn.edu.hcnu.manager.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrganizationRecordCommand {
    private String userId;
    private String organizationId;
    private String content;
}
