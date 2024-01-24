package cc.cloudcoding.manager.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionCommand {
    private Long id;

    private Long organizationId;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    private String status;
}
