package cn.edu.hcnu.course.model.res;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeRes {
    private String id;

    private String unitId;

    private String url;

    private String name;

    private String type;

    private String description;
}
