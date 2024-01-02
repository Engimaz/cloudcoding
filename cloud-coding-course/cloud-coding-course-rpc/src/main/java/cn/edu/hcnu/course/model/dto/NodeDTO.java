package cn.edu.hcnu.course.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeDTO {
    private String id;

    private String unitId;

    private String url;

    private String name;

    private String type;

    private String description;
}
