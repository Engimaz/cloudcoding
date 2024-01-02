package cn.edu.hcnu.resource.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileQueryDTO<T> {
    private List<T> list;
    private Long count;
}
