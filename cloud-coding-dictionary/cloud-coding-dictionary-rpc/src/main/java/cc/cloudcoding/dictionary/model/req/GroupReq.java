package cc.cloudcoding.dictionary.model.req;

import cc.cloudcoding.dictionary.model.dto.DictionaryDTO;
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
