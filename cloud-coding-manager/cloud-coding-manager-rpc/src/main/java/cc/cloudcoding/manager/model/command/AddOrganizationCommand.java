package cc.cloudcoding.manager.model.command;

import cc.cloudcoding.manager.model.dto.FeatureDTO;
import cc.cloudcoding.manager.model.dto.PositionDTO;
import cc.cloudcoding.manager.model.dto.UserPositionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 16:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrganizationCommand {
    private String name;
    private String status;
    private String avatar;
    private String img;
    private String description;
    private String address;
    private String location;
    private String type;

    private List<FeatureDTO> features;
    private List<PositionDTO> positions;
    private List<UserPositionDTO> userPositions;
}
