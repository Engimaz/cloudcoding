package cn.edu.hcnu.dictionary.model.req;

import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupReq {
    private Long id;
    private String name;
    private String description;
    private List<DictionaryDTO> list;
    public GroupReq(String name, List<DictionaryDTO> list) {
        this.name = name;
        this.list = list;
    }
}
