package cn.edu.hcnu.program.model.res;

import cn.edu.hcnu.program.model.dto.ProgramUserDTO;
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
