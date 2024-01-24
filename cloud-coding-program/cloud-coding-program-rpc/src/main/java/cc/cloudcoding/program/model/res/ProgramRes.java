package cc.cloudcoding.program.model.res;

import cc.cloudcoding.program.model.dto.ProgramUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramRes {
    private String name;
    private String id;
    private String description;
    private String language;
    private String sdk;
    private String avatar;
    private List<ProgramUserDTO> relations;
}
