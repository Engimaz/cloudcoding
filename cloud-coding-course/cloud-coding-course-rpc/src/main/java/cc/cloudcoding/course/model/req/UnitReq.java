package cc.cloudcoding.course.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitReq {
    private String name;
    private Integer order;
    private List<NodeReq> nodes;
}
