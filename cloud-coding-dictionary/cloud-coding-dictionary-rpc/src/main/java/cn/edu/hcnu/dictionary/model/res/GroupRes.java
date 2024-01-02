package cn.edu.hcnu.dictionary.model.res;

import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/29 23:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRes {
    private String id;
    private String name;
    private String description;
    private List<DictionaryDTO> list;
}
