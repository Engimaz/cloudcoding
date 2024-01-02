package cn.edu.hcnu.program.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 2:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionInfoDTO {
    private String outputData;
    /**
     * 错误数据
     */
    private String errorData;
}
