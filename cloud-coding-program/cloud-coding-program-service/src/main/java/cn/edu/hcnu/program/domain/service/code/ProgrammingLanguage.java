package cn.edu.hcnu.program.domain.service.code;

import cn.edu.hcnu.program.domain.service.program.Program;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;

/**
 * 编程语言
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/07/01
 */
public interface ProgrammingLanguage {


    ExecutionInfoDTO execute(Program program);
}
