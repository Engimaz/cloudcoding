package cn.edu.hcnu.resource.controller;

import cn.edu.hcnu.base.model.*;
import cn.edu.hcnu.resource.application.service.IFileApplication;
import cn.edu.hcnu.resource.assembler.ResourceDTOToResourceResMapping;
import cn.edu.hcnu.resource.model.command.UploadResourceCommand;
import cn.edu.hcnu.resource.model.dto.ResourceDTO;
import cn.edu.hcnu.resource.model.query.ResourceQuery;
import cn.edu.hcnu.resource.model.req.MergeResourceParamsReq;
import cn.edu.hcnu.resource.model.req.QueryResourceParamsReq;
import cn.edu.hcnu.resource.model.res.ResourceRes;
import cn.edu.hcnu.resource.rpc.FileApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/23 18:18
 */
@Tag(name = "资源管理接口")
@RestController
@Slf4j
@RequestMapping("/resource")
public class FilesController implements FileApi {

    @Autowired
    private IFileApplication fileApplication;

    @Autowired
    private ResourceDTOToResourceResMapping resourceDTOToResourceResMapping;

    @PostMapping("/files")
    @Operation(summary = "媒资列表查询接口")
    @Parameters({
            @Parameter(name = "pageParams", description = "分页查询条件", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "queryMediaParamsDto", description = "查询参数", required = true),
            @Parameter(name = "userId", description = "用户id", required = true, in = ParameterIn.QUERY)
    })
    @Override
    public RestResponse<PageRes<ResourceRes, ResourceQuery>> list(PageParams pageParams, @RequestParam("userId") String userId, @RequestBody QueryResourceParamsReq queryMediaParamsDto) {

        ResourceQuery resourceQuery = new ResourceQuery();
        resourceQuery.setPage(pageParams.getPage());
        resourceQuery.setSize(pageParams.getSize());
        resourceQuery.setKeyword(pageParams.getKeyword());
        resourceQuery.setUserId(userId);
        resourceQuery.setName(queryMediaParamsDto.getName());
        resourceQuery.setState(queryMediaParamsDto.getState());
        resourceQuery.setType(queryMediaParamsDto.getType());
        PageDTO<ResourceDTO, ResourceQuery> query = fileApplication.query(resourceQuery);

        PageRes<ResourceRes, ResourceQuery> res = new PageRes<>();
        res.setList(resourceDTOToResourceResMapping.sourceToTarget(query.getList()));
        res.setCount(query.getCount());
        res.setCommonQuery(resourceQuery);
        return RestResponse.success(ResultCode.SUCCESS, res);

    }

    @PostMapping(value = "/upload/file")
    @Operation(summary = "上传文件")
    @Parameters({
            @Parameter(name = "file", description = "上传文件", required = true),
            @Parameter(name = "userId", description = "用户id", required = true),
            @Parameter(name = "md5", description = "自己本身的md5", required = true),
            @Parameter(name = "chunkMd5", description = "分块", required = false)
    })
    @Override
    public RestResponse upload(@RequestParam("file") MultipartFile upload, @RequestParam("userId") @NotBlank String userId, @RequestParam("md5") String md5, @RequestParam("chunkMd5") String chunkMd5) throws IOException {

        //创建临时文件
        File tempFile = File.createTempFile("minio-", upload.getOriginalFilename());

        //上传的文件拷贝到临时文件
        upload.transferTo(tempFile);

        UploadResourceCommand uploadResourceCommand = new UploadResourceCommand();

        uploadResourceCommand.setUploadResourceAbsolutePath(tempFile.getAbsolutePath());
        uploadResourceCommand.setUserId(userId);
        uploadResourceCommand.setChunkMd5(chunkMd5);
        uploadResourceCommand.setMd5(md5);
        uploadResourceCommand.setSize(upload.getSize());
        uploadResourceCommand.setName(md5 + upload.getOriginalFilename());
        uploadResourceCommand.setContentType(upload.getContentType());

        ResourceDTO d = fileApplication.uploadResource(uploadResourceCommand);

        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, resourceDTOToResourceResMapping.sourceToTarget(d));
        }
        if (tempFile.isFile()) {
            tempFile.delete();
        }
        return RestResponse.fail(ResultCode.FILE_UPLOAD_FAIL);
    }


    @Operation(summary = "文件上传前检查文件", description = "true:文件已经上传过;false文件未上传")
    @GetMapping("/upload/file/check/{md5}")
    @Override
    public RestResponse checkFile(@PathVariable("md5") String fileMd5) {
        ResourceDTO d = fileApplication.checkResourceByMd5(fileMd5);
        if (d != null) {
            return RestResponse.success(ResultCode.FILE_CHECK_FAIL, resourceDTOToResourceResMapping.sourceToTarget(d));
        }

        return RestResponse.success(ResultCode.SUCCESS, true);
    }


    @Operation(summary = "合并文件")
    @PutMapping("/upload/chunk/merge")
    @Override
    public RestResponse mergeChunks(@RequestBody MergeResourceParamsReq req) throws UnsupportedEncodingException {

        ResourceDTO d = fileApplication.mergeResource(req.getMd5(), req.getName(), req.getUserId(), URLDecoder.decode(req.getType(), "UTF-8"));
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, resourceDTOToResourceResMapping.sourceToTarget(d));
        }

        return RestResponse.fail(ResultCode.MERGER_ERROR);
    }

    @Operation(summary = "获得文件访问信息")
    @GetMapping("/{id}")
    public void getFileAccessInfo(@PathVariable("id") String id, HttpServletResponse httpResponse) {
        String url = fileApplication.getEnterPoint(id);
        try {
            httpResponse.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Operation(summary = "获得文件访问信息")
    @PostMapping("/{id}")
    public void getFileAccessInfoPost(@PathVariable("id") String id, HttpServletResponse httpResponse) {
        String url = fileApplication.getEnterPoint(id);
        try {
            httpResponse.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("{id}")
    public RestResponse deleteResource(@PathVariable("id") String id) {
        boolean b = fileApplication.deleteResource(id);
        if (b) {
            return RestResponse.success(ResultCode.SUCCESS);
        }
        return RestResponse.fail(ResultCode.DELETE_FILE_ERROR);
    }


}
