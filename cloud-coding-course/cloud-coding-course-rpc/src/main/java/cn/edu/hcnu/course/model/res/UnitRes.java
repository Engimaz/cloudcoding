package cn.edu.hcnu.course.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitRes {
    private String id;

    private String courseId;

    private String name;


    private Integer order;
    private List<NodeRes> nodes;
}
