package cc.cloudcoding.program.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDTO {
    private String name;
    private String id;
    private String description;
    private String language;
    private String sdk;
    private String avatar;
    private List<ProgramUserDTO> relations;
}
