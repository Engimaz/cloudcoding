package cc.cloudcoding.program.model.req;

import cc.cloudcoding.program.model.dto.ProgramUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/12 0:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramReq {
    private String name;
    private String id;
    private String description;
    private String language;
    private String sdk;
    private String command;
    private String avatar;
    // 能访问这个项目的人
    private List<ProgramUserDTO> relations;
}
