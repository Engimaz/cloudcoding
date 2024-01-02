package cn.edu.hcnu.resource.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceQuery {
    private Integer page;
    private Integer size;
    private String keyword;
    private  String userId;
    private String name;
    private String type;
    private String state;
}
