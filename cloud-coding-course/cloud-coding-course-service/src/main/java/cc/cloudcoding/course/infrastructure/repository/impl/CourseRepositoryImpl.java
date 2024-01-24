package cc.cloudcoding.course.infrastructure.repository.impl;

import cc.cloudcoding.course.infrastructure.mapper.CourseMapper;
import cc.cloudcoding.course.infrastructure.repository.CourseRepository;
import cc.cloudcoding.course.model.po.CoursePO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Service
public class CourseRepositoryImpl extends ServiceImpl<CourseMapper, CoursePO> implements CourseRepository {

}
