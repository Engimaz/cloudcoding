package cc.cloudcoding.program.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFolderCommand {
    private String id;
    private String name;
    private String parentId;
    private String programId;
}
