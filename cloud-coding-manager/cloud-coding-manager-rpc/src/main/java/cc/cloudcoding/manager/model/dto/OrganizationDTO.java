package cc.cloudcoding.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {
    private Long id;
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
