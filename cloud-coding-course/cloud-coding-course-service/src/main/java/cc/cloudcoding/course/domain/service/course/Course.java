package cc.cloudcoding.course.domain.service.course;

import cc.cloudcoding.course.domain.service.unit.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String id;
    private String avatar;
    private String name;
    private String description;
    private List<Unit> units;
}
