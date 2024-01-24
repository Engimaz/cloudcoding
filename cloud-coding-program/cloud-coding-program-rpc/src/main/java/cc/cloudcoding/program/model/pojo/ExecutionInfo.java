package cc.cloudcoding.program.model.pojo;

import lombok.Data;


/**
 * 执行信息
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/07/01
 */
@Data
public class ExecutionInfo {
    /**
     * 输出数据
     */
    private String outputData;
    /**
     * 错误数据
     */
    private String errorData;
}
