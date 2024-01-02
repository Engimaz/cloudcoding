package cn.edu.hcnu.program.controller;

import cn.edu.hcnu.program.application.service.IFileApplication;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import cn.edu.hcnu.program.assembler.FileDTOToFileResMapping;
import cn.edu.hcnu.program.assembler.FileReqToAddFileCommandMapping;
import cn.edu.hcnu.program.assembler.FileReqToUpdateFileCommandMapping;
import cn.edu.hcnu.program.model.command.AddFileCommand;
import cn.edu.hcnu.program.model.dto.FileDTO;
import cn.edu.hcnu.program.model.req.FileReq;
import cn.edu.hcnu.program.model.res.FileRes;
import cn.edu.hcnu.program.rpc.FileApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("file")
public class FileController implements FileApi {

    @Autowired
    private FileReqToUpdateFileCommandMapping fileReqToUpdateFileCommandMapping;

    @Autowired
    private FileReqToAddFileCommandMapping fileReqToAddFileCommandMapping;

    @Autowired
    private IFileApplication iFileApplication;

    @Autowired
    private FileDTOToFileResMapping fileDTOToFileResMapping;

    @Override
    @PostMapping("new")
    public RestResponse addFile(@RequestBody FileReq fileReq) {
        AddFileCommand addFileCommand = fileReqToAddFileCommandMapping.sourceToTarget(fileReq);
        FileDTO fileDTO = iFileApplication.save(addFileCommand);
        if (fileDTO != null) {
            FileRes fileRes = fileDTOToFileResMapping.sourceToTarget(fileDTO);
            return RestResponse.success(ResultCode.SUCCESS, fileRes);
        }
        return RestResponse.fail(ResultCode.ADD_FILE_ERROR);
    }

    @Override
    @DeleteMapping("{id}")
    public RestResponse removeFile(@PathVariable String id) {
        boolean res = iFileApplication.remove(id);
        if (res) {
            return RestResponse.success(ResultCode.SUCCESS);
        }
        return RestResponse.fail(ResultCode.REMOVE_FILE_ERROR);
    }

    @Override
    @GetMapping("{id}")
    public RestResponse queryFile(@PathVariable("id") String id) {
        FileDTO fileDTO = iFileApplication.queryById(id);
        if (fileDTO != null) {
            FileRes fileRes = fileDTOToFileResMapping.sourceToTarget(fileDTO);
            return RestResponse.success(ResultCode.SUCCESS, fileRes);
        }
        return RestResponse.fail(ResultCode.QUERY_FILE_ERROR);
    }

    @Override
    @PutMapping("update")
    public RestResponse updateFile(@RequestBody FileReq fileReq) {
        FileDTO fileDTO = iFileApplication.update(fileReqToUpdateFileCommandMapping.sourceToTarget(fileReq));
        if (fileDTO != null) {
            FileRes fileRes = fileDTOToFileResMapping.sourceToTarget(fileDTO);
            return RestResponse.success(ResultCode.SUCCESS, fileRes);
        }
        return RestResponse.fail(ResultCode.UPDATE_FILE_ERROR);
    }
}
