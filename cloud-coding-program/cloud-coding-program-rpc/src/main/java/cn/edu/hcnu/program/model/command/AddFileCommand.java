package cn.edu.hcnu.program.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFileCommand {
    private String name;
    private String folderId;
    private String content;
}
