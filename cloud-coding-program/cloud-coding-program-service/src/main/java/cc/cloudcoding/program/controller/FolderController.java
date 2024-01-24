package cc.cloudcoding.program.controller;

import cc.cloudcoding.program.application.service.IFolderApplication;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.base.model.ResultCode;
import cc.cloudcoding.program.assembler.FolderDTOToFolderResMapping;
import cc.cloudcoding.program.assembler.FolderReqToAddFolderCommandMapping;
import cc.cloudcoding.program.assembler.FolderReqToUpdateFolderCommandMapping;
import cc.cloudcoding.program.model.command.AddFolderCommand;
import cc.cloudcoding.program.model.dto.FolderDTO;
import cc.cloudcoding.program.model.req.FolderReq;
import cc.cloudcoding.program.model.res.FolderRes;
import cc.cloudcoding.program.rpc.FolderApi;
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
