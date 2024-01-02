package cn.edu.hcnu.auth.domain.service.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {

    private Long id;

    private Long userId;


    private Integer identityType;


    private String identifier;


    private String credential;
}
