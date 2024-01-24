package cc.cloudcoding.manager.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 16:34
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationReq {
    private String id;
    private String name;
    private String status;
    private String avatar;
    private String img;
    private String description;
    private String address;
    private String location;
    private String type;
    // 组织的功能
    private List<FeatureReq> features;
    // 组织的职位
    private List<PositionReq> positions;
    // 组织的成员
    private List<UserPositionReq> userPositions;
}
