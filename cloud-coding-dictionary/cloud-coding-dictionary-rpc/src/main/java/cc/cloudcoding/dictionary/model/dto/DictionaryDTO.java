package cc.cloudcoding.dictionary.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 12:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryDTO implements Serializable {
    private Long id;
    private String label;
    private String value;

}
