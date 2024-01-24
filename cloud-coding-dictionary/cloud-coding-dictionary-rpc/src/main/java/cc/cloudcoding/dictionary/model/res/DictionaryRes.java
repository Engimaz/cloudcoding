package cc.cloudcoding.dictionary.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/14 22:23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryRes {
    private String id;
    private String label;
    private String value;
}
