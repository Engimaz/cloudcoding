package cn.edu.hcnu.course.rpc;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.course.model.req.AddCourseReq;
import cn.edu.hcnu.course.model.req.UpdateCourseReq;
import cn.edu.hcnu.course.model.res.CourseRes;
import org.springframework.web.bind.annotation.*;

public interface ICourseApi{
    RestResponse list(CommonQuery query);

    RestResponse deletecourse( String id);

    RestResponse<CourseRes> addcourse( AddCourseReq addCourseReq);

    RestResponse<CourseRes> addcourse( UpdateCourseReq updateCourseReq);

    @GetMapping("{id}")
    RestResponse<CourseRes> addcourse(@PathVariable("id") String id);
}
