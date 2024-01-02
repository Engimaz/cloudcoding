package cn.edu.hcnu.program.rpc;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.program.model.req.ProgramReq;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface ProgramApi {
    RestResponse addProgram(ProgramReq program);

    RestResponse updateProgram(ProgramReq programReq);

    RestResponse listProgramByUserId(String userId);


    RestResponse runProgram(ProgramReq req);

    @PutMapping("/stop/{programId}")
    RestResponse runProgram(@PathVariable("programId") String programId);
}
