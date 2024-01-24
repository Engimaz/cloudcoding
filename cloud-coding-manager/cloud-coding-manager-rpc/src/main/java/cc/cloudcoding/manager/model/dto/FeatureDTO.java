package cc.cloudcoding.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 14:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDTO {
    private String id;
    private String name;
    private String status;
    private String value;
    private String description;
    private List<String> urls;
}
