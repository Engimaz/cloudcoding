package cn.edu.hcnu.manager.model.req;

import cn.edu.hcnu.manager.model.dto.FeatureUrlDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 15:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureReq {
    private String id;
    private String name;
    private String status;
    private String value;
    private String description;
    private List<String> urls;
}
