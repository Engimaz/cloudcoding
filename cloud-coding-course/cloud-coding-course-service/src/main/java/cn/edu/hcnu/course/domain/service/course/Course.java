package cn.edu.hcnu.course.domain.service.course;

import cn.edu.hcnu.course.domain.service.unit.Unit;
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
