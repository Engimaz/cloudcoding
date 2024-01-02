package cn.edu.hcnu.course.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeCommand {

    private String url;

    private String name;

    private String type;

    private String description;
}
