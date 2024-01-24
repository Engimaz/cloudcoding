package cc.cloudcoding.program.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramUserDTO {
    private String id;
    private String programId;
    private String userId;
    private String role;
}
