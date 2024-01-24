package cc.cloudcoding.program.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFolderCommand {
    private String name;
    private String parentId;
    private String projectId;
    private List<UpdateFolderCommand> folders;
    private List<UpdateFileCommand> files;
}
