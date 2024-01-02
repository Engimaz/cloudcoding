package cn.edu.hcnu.course.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseCommand {
    private String id;
    private String avatar;
    private String name;
    private String description;
    private List<UnitCommand> units;
}
