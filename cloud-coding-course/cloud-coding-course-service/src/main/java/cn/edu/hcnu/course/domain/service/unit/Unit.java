package cn.edu.hcnu.course.domain.service.unit;

import cn.edu.hcnu.course.domain.service.node.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    private String id;
    private String courseId;
    private String name;
    private Integer order;
    private List<Node> nodes;
}
