package cc.cloudcoding.dictionary.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryPageQuery {
    @Min(value = 1, message = "page必须大于等于1")
    private Integer page;
    @Min(value = 1, message = "size必须大于等于1")
    private Integer size;
    private String keyword;
}
