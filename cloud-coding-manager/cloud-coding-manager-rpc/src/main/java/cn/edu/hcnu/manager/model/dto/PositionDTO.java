package cn.edu.hcnu.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/4 19:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {
    private String id;
    private String name;
    private String status;
    private String value;
    private String organizationId;
}
