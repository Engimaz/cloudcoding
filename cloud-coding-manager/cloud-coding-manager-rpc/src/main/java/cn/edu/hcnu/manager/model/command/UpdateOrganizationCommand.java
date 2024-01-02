package cn.edu.hcnu.manager.model.command;

import cn.edu.hcnu.manager.model.dto.FeatureDTO;
import cn.edu.hcnu.manager.model.dto.PositionDTO;
import cn.edu.hcnu.manager.model.dto.UserPositionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 16:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrganizationCommand {
    private Long id;
    private String name;
    private String status;
    private String avatar;
    private String img;
    private String description;
    private String address;
    private String location;
    private String type;

    // 功能
    private List<FeatureDTO> features;
    // 职位信息
    private List<PositionDTO> positions;
    // 用户与职位的关系
    private List<UserPositionDTO> userPositions;

}
