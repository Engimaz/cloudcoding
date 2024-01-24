package cc.cloudcoding.course.rpc;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.course.model.req.AddCourseReq;
import cc.cloudcoding.course.model.req.UpdateCourseReq;
import cc.cloudcoding.course.model.res.CourseRes;
import org.springframework.web.bind.annotation.*;

public interface ICourseApi{
    RestResponse list(CommonQuery query);

    RestResponse deletecourse( String id);

    RestResponse<CourseRes> addcourse(AddCourseReq addCourseReq);

    RestResponse<CourseRes> addcourse( UpdateCourseReq updateCourseReq);

    @GetMapping("{id}")
    RestResponse<CourseRes> addcourse(@PathVariable("id") String id);
}
