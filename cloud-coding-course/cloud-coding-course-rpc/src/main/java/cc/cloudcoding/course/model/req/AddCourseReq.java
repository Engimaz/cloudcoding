package cc.cloudcoding.course.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseReq {
    private String avatar;
    private String name;
    private String description;
    private List<UnitReq> units;
}
