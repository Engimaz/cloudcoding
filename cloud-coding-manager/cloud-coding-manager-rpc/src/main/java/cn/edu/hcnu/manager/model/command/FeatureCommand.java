package cn.edu.hcnu.manager.model.command;

import cn.edu.hcnu.manager.model.dto.FeatureUrlDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureCommand {
    private Long id;
    private String name;
    private String status;

    private List<FeatureUrlDTO> relations;
}
