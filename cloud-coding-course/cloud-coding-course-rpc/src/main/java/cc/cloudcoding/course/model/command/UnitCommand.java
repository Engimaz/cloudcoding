package cc.cloudcoding.course.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitCommand {
    private String name;
    private Integer order;
    private List<NodeCommand> nodes;
}
