package cc.cloudcoding.dictionary.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 12:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private List<DictionaryDTO> list;
}
