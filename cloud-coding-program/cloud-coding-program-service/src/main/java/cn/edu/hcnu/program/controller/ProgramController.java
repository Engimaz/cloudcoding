package cn.edu.hcnu.program.controller;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import cn.edu.hcnu.program.application.service.IProgramApplication;
import cn.edu.hcnu.program.assembler.*;
import cn.edu.hcnu.program.model.command.AddProgramCommand;
import cn.edu.hcnu.program.model.command.ExecuteCommand;
import cn.edu.hcnu.program.model.command.UpdateProgramCommand;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;
import cn.edu.hcnu.program.model.dto.ProgramDTO;
import cn.edu.hcnu.program.model.req.ProgramReq;
import cn.edu.hcnu.program.model.res.ProgramRes;
import cn.edu.hcnu.program.rpc.ProgramApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("program")
public class ProgramController implements ProgramApi {

    @Autowired
    private ProgramReqToAddProgramCommandMapping programReqToAddProgramCommandMapping;

    @Autowired
    @Qualifier("programApplicationImpl")
    private IProgramApplication iProgramApplication;

    @Autowired
    private ProgramDTOToProgramResMapping programDTOToProgramResMapping;

    @Autowired
    private ProgramReqToUpdateProgramCommandMapping programReqToUpdateProgramCommandMapping;

    @Autowired
    private ProgramReqToExecuteCommandMapping programReqToExecuteCommandMapping;

    @Override
    @PostMapping("new")
    public RestResponse addProgram(@RequestBody ProgramReq program) {
        AddProgramCommand addProgramCommand = programReqToAddProgramCommandMapping.sourceToTarget(program);
        ProgramDTO programDTO = iProgramApplication.save(addProgramCommand);
        if (programDTO != null) {
            ProgramRes programRes = programDTOToProgramResMapping.sourceToTarget(programDTO);
            return RestResponse.success(ResultCode.SUCCESS, programRes);
        }
        return RestResponse.fail(ResultCode.PROGRAM_CREATE_ERROR);
    }

    @Override
    @PutMapping("update")
    public RestResponse updateProgram(@RequestBody ProgramReq programReq) {
        UpdateProgramCommand updateProgramCommand = programReqToUpdateProgramCommandMapping.sourceToTarget(programReq);
        ProgramDTO programDTO = iProgramApplication.update(updateProgramCommand);
        if (programDTO != null) {
            ProgramRes programRes = programDTOToProgramResMapping.sourceToTarget(programDTO);
            return RestResponse.success(ResultCode.SUCCESS, programRes);
        }
        return RestResponse.fail(ResultCode.PROGRAM_UPDATE_ERROR);
    }

    @Override
    @GetMapping("/list/{userId}")
    public RestResponse listProgramByUserId(@PathVariable("userId") String userId) {
        List<ProgramDTO> list = iProgramApplication.queryProgramByUserId(userId);
        List<ProgramRes> programRes = programDTOToProgramResMapping.sourceToTarget(list);
        if (programRes != null) {
            return RestResponse.success(ResultCode.SUCCESS, programRes);
        }

        return RestResponse.fail(ResultCode.PROGRAM_QUERY_ERROR);
    }

    @Autowired
    private ExecutionInfoDTOToExecutionResMapping executionInfoDTOToExecutionResMapping;

    @Override
    @PostMapping("/run")
    public RestResponse runProgram(@RequestBody ProgramReq req) {
        ExecuteCommand executeCommand = programReqToExecuteCommandMapping.sourceToTarget(req);

        ExecutionInfoDTO infoDTO = iProgramApplication.execute(executeCommand);
        if (infoDTO != null) {
            return RestResponse.success(ResultCode.SUCCESS, executionInfoDTOToExecutionResMapping.sourceToTarget(infoDTO));
        }
        return RestResponse.fail(ResultCode.PROGRAM_RUN_ERROR);
    }

    @Override
    @PutMapping("/stop/{programId}")
    public RestResponse runProgram(@PathVariable("programId") String programId) {

        ExecutionInfoDTO infoDTO = iProgramApplication.stopContainer("p-" + programId);
        if (infoDTO != null) {
            return RestResponse.success(ResultCode.SUCCESS, executionInfoDTOToExecutionResMapping.sourceToTarget(infoDTO));
        }
        return RestResponse.fail(ResultCode.PROGRAM_RUN_ERROR);
    }

}
