package cn.edu.hcnu.course.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {
    private String id;
    private String courseId;
    private String name;
    private Integer order;
    private List<NodeDTO> nodes;
}
