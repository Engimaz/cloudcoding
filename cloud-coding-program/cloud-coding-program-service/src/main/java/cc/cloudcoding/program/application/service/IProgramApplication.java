package cc.cloudcoding.program.application.service;

import cc.cloudcoding.program.model.command.AddProgramCommand;
import cc.cloudcoding.program.model.command.ExecuteCommand;
import cc.cloudcoding.program.model.command.UpdateProgramCommand;
import cc.cloudcoding.program.model.dto.ExecutionInfoDTO;
import cc.cloudcoding.program.model.dto.ProgramDTO;

import java.util.List;

public interface IProgramApplication {
    ProgramDTO save(AddProgramCommand addProgramCommand);

    ProgramDTO update(UpdateProgramCommand updateProgramCommand);

    List<ProgramDTO> queryProgramByUserId(String userId);

    ExecutionInfoDTO execute(ExecuteCommand executeCommand);

    ExecutionInfoDTO stopContainer(String containerName);
}
