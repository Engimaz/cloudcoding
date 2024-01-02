package cn.edu.hcnu.program.domain.service.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramUser {
    private Long id;
    private String programId;
    private String userId;
    private String role;
}
