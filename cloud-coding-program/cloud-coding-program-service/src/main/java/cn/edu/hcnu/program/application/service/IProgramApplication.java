package cn.edu.hcnu.program.application.service;

import cn.edu.hcnu.program.model.command.AddProgramCommand;
import cn.edu.hcnu.program.model.command.ExecuteCommand;
import cn.edu.hcnu.program.model.command.UpdateProgramCommand;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;
import cn.edu.hcnu.program.model.dto.ProgramDTO;

import java.util.List;

public interface IProgramApplication {
    ProgramDTO save(AddProgramCommand addProgramCommand);

    ProgramDTO update(UpdateProgramCommand updateProgramCommand);

    List<ProgramDTO> queryProgramByUserId(String userId);

    ExecutionInfoDTO execute(ExecuteCommand executeCommand);

    ExecutionInfoDTO stopContainer(String containerName);
}
