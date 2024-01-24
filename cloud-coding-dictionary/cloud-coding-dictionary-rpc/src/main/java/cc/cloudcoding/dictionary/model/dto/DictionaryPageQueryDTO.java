package cc.cloudcoding.dictionary.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 15:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryPageQueryDTO {
    private Long count;
    private List<DictionaryDTO> list;
}
