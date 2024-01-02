package cn.edu.hcnu.dictionary.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:59
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGroupCommand {
    @NotBlank(message = "id不能为空")
    private Long id;
    @NotBlank(message = "name不能为空")
    private String name;
    @NotBlank(message = "描述不能为空")
    private String description;
    private List<DictionaryCommand> list;
}
