package cn.edu.hcnu.program.controller;

import cn.edu.hcnu.program.application.service.IFolderApplication;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import cn.edu.hcnu.program.assembler.FolderDTOToFolderResMapping;
import cn.edu.hcnu.program.assembler.FolderReqToAddFolderCommandMapping;
import cn.edu.hcnu.program.assembler.FolderReqToUpdateFolderCommandMapping;
import cn.edu.hcnu.program.model.command.AddFolderCommand;
import cn.edu.hcnu.program.model.dto.FolderDTO;
import cn.edu.hcnu.program.model.req.FolderReq;
import cn.edu.hcnu.program.model.res.FolderRes;
import cn.edu.hcnu.program.rpc.FolderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("folder")
public class FolderController implements FolderApi {

    @Autowired
    private IFolderApplication iFolderApplication;

    @Autowired
    private FolderReqToAddFolderCommandMapping folderReqToAddFolderCommandMapping;

    @Autowired
    private FolderDTOToFolderResMapping folderDTOToFolderResMapping;

    @Autowired
    private FolderReqToUpdateFolderCommandMapping folderReqToUpdateFolderCommandMapping;

    @Override
    @PostMapping("new")
    public RestResponse addFolder(@RequestBody FolderReq folder) {

        AddFolderCommand addFolderCommand = folderReqToAddFolderCommandMapping.sourceToTarget(folder);
        FolderDTO folderDTO = iFolderApplication.addFolder(addFolderCommand);
        if (folderDTO != null) {
            FolderRes folderRes = folderDTOToFolderResMapping.sourceToTarget(folderDTO);
            return RestResponse.success(ResultCode.SUCCESS, folderRes);
        }
        return RestResponse.fail(ResultCode.ADD_FOLDER_ERROR);
    }

    @Override
    @DeleteMapping("{id}")
    public RestResponse removeFolder(@PathVariable String id) {
        boolean res = iFolderApplication.removeFolder(id);
        if (res) {
            return RestResponse.success(ResultCode.SUCCESS);
        }
        return RestResponse.fail(ResultCode.REMOVE_FOLDER_ERROR);
    }

    @GetMapping("{id}")
    @Override
    public RestResponse queryFolder(@PathVariable String id) {
        FolderDTO folderDTO = iFolderApplication.queryFolder(id);
        FolderRes folderRes = folderDTOToFolderResMapping.sourceToTarget(folderDTO);
        return RestResponse.success(ResultCode.SUCCESS,folderRes);
    }
    @GetMapping("top/{id}")
    @Override
    public RestResponse queryTopFolder(@PathVariable String id) {
        FolderDTO folderDTO = iFolderApplication.queryTopFolder(id);
        FolderRes folderRes = folderDTOToFolderResMapping.sourceToTarget(folderDTO);
        return RestResponse.success(ResultCode.SUCCESS,folderRes);
    }

    @PutMapping("update")
    @Override
    public RestResponse updateFolder(@RequestBody FolderReq folderReq) {
        FolderDTO folderDTO=   iFolderApplication.updateFolder(folderReqToUpdateFolderCommandMapping.sourceToTarget(folderReq));
        if (folderDTO != null) {
            FolderRes folderRes = folderDTOToFolderResMapping.sourceToTarget(folderDTO);
            return RestResponse.success(ResultCode.SUCCESS, folderRes);
        }
        return RestResponse.fail(ResultCode.UPDATE_FOLDER_ERROR);
    }
}
