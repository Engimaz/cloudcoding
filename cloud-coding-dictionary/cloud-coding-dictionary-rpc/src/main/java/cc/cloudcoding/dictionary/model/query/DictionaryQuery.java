package cc.cloudcoding.dictionary.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 16:26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryQuery {
    private Long id;
    private String label;
    private String value;

}
