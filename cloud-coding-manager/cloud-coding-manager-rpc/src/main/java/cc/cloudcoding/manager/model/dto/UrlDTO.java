package cc.cloudcoding.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/2 23:17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlDTO implements Serializable {
    private Long id;
    private String name;
    private String value;
    private String status;
    private String scope;
    private String description;
}
