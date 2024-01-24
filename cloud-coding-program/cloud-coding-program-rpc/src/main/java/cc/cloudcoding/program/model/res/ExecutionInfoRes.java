package cc.cloudcoding.program.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 15:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionInfoRes {
    private String outputData;
    /**
     * 错误数据
     */
    private String errorData;
}
