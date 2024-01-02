package cn.edu.hcnu.course.controller;

import cn.edu.hcnu.base.model.*;
import cn.edu.hcnu.course.application.service.ICourseApplication;
import cn.edu.hcnu.course.controller.assembler.AddCourseReqToAddCourseCommandMapping;
import cn.edu.hcnu.course.controller.assembler.CourseDTOToCourseResMapping;
import cn.edu.hcnu.course.controller.assembler.UpdateCourseReqToUpdateCourseCommandMapping;
import cn.edu.hcnu.course.model.command.AddCourseCommand;
import cn.edu.hcnu.course.model.command.UpdateCourseCommand;
import cn.edu.hcnu.course.model.dto.CourseDTO;
import cn.edu.hcnu.course.rpc.ICourseApi;
import cn.edu.hcnu.course.model.req.AddCourseReq;
import cn.edu.hcnu.course.model.req.UpdateCourseReq;
import cn.edu.hcnu.course.model.res.CourseRes;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@DubboService(group = "course")
@RequestMapping("course")
public class CourseController implements ICourseApi {

    @Autowired
    private ICourseApplication courseApplication;

    @Autowired
    private AddCourseReqToAddCourseCommandMapping addCourseReqToAddCourseCommandMapping;
    @Autowired
    private UpdateCourseReqToUpdateCourseCommandMapping updateCourseReqToUpdateCourseCommandMapping;
    @Autowired
    private CourseDTOToCourseResMapping courseDTOToCourseResMapping;

    @Override
    @GetMapping("list")
    public RestResponse list(CommonQuery query) {
        PageDTO<CourseDTO, CommonQuery> pageDTO = courseApplication.list(query);
        if (pageDTO != null) {
            List<CourseRes> courseRes = courseDTOToCourseResMapping.sourceToTarget(pageDTO.getList());
            return RestResponse.success(ResultCode.SUCCESS, new PageRes<>(courseRes, pageDTO.getCount(), pageDTO.getCommonQuery()));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }


    @Override
    @DeleteMapping("{id}")
    public RestResponse deletecourse(@PathVariable("id") String id) {
        boolean res = courseApplication.delete(id);
        if (res) {
            return RestResponse.success(ResultCode.SUCCESS);
        }
        return null;
    }

    @Override
    @PostMapping("new")
    public RestResponse<CourseRes> addcourse(@RequestBody AddCourseReq addCourseReq) {
        AddCourseCommand addcourseCommand = addCourseReqToAddCourseCommandMapping.sourceToTarget(addCourseReq);
        CourseDTO d = courseApplication.save(addcourseCommand);
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, courseDTOToCourseResMapping.sourceToTarget(d));
        }
        return RestResponse.fail(ResultCode.SAVE_ERROR);
    }

    @Override
    @PutMapping("update")
    public RestResponse<CourseRes> addcourse(@RequestBody UpdateCourseReq updateCourseReq) {
        UpdateCourseCommand command = updateCourseReqToUpdateCourseCommandMapping.sourceToTarget(updateCourseReq);
        CourseDTO d = courseApplication.update(command);
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, courseDTOToCourseResMapping.sourceToTarget(d));
        }
        return RestResponse.fail(ResultCode.UPDATE_ERROR);
    }
    @Override
    @GetMapping("{id}")
    public RestResponse<CourseRes> addcourse(@PathVariable("id") String id) {
        CourseDTO d = courseApplication.getById(id);
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, courseDTOToCourseResMapping.sourceToTarget(d));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }

}
