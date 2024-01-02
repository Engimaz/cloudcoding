package cn.edu.hcnu.program.domain.service.program;

import cn.edu.hcnu.program.domain.service.relation.ProgramUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program {
    private String name;
    private String id;
    private String description;
    private String language;
    private String sdk;
    private List<ProgramUser> relations;
}
