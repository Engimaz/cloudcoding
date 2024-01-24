package cc.cloudcoding.course.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeReq {

    private String url;

    private String name;

    private String type;

    private String description;
}
