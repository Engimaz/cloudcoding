package cn.edu.hcnu.manager.model.command;

import cn.edu.hcnu.manager.model.dto.FeatureUrlDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 14:54
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFeatureCommand {
    private Long id;
    private String name;
    private String status;

    private List<FeatureUrlDTO> relations;
}
