package cc.cloudcoding.program.domain.service.code;

import cc.cloudcoding.program.domain.service.program.Program;
import cc.cloudcoding.program.model.dto.ExecutionInfoDTO;

/**
 * 编程语言
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/07/01
 */
public interface ProgrammingLanguage {


    ExecutionInfoDTO execute(Program program);
}
