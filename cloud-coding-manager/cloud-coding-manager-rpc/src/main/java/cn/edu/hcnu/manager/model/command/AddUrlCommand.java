package cn.edu.hcnu.manager.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/2 23:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUrlCommand {
    private String name;
    private String value;
    private String status;
    private String scope;
    private String description;
}
