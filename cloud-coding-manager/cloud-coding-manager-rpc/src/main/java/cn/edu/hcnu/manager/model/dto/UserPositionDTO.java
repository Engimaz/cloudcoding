package cn.edu.hcnu.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPositionDTO {
    private String id;
    private String position;
    private String userId;
    private String positionId;
}
