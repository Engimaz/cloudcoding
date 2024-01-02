package cn.edu.hcnu.program.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 1:57
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCommand {
    private String id;
    private String command;
}
