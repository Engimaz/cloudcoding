package cn.edu.hcnu.manager.model.res;

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
 * @time: 2023/10/4 18:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRes {
    private String id;
    private String name;
    private String status;
    private String avatar;
    private String img;
    private String description;
    private String address;
    private String location;
    private String type;
    // 社团有的职位
    private List<PositionDTO> positions;
    // 社团有的功能
    private List<FeatureDTO> features;
    // 社团成员与职位的关系
    private List<UserPositionDTO> userPositions;
}
