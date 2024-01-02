package cn.edu.hcnu.dictionary.model.res;

import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/26 12:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupPageQueryRes {
    private String id;
    private String name;
    private List<DictionaryDTO> list;
}
