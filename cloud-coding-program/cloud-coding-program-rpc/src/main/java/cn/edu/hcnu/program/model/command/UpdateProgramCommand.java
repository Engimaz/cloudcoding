package cn.edu.hcnu.program.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProgramCommand {
    private String name;
    private String id;
    private String description;
    private String language;


    private String sdk;

}
