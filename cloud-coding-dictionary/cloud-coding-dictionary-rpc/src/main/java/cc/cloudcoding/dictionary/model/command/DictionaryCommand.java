package cc.cloudcoding.dictionary.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 12:52
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryCommand {
    private Long id;
    private String label;
    private String value;

}
