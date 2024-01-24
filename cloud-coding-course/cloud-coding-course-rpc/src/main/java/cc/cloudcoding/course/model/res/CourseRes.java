package cc.cloudcoding.course.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRes {
    private String id;

    private String avatar;

    private String name;

    private String description;
    private List<UnitRes> units;
}
