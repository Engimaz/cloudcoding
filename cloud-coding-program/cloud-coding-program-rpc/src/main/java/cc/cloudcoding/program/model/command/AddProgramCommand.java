package cc.cloudcoding.program.model.command;

import cc.cloudcoding.program.model.dto.ProgramUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProgramCommand {

    private String name;
    private String description;
    private String language;
    private String avatar;
    private String sdk;
    private List<ProgramUserDTO> relations;
}
