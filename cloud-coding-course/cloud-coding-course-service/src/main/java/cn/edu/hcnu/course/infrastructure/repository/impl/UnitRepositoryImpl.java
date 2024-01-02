package cn.edu.hcnu.course.infrastructure.repository.impl;

import cn.edu.hcnu.course.infrastructure.mapper.UnitMapper;
import cn.edu.hcnu.course.infrastructure.repository.UnitRepository;
import cn.edu.hcnu.course.model.po.UnitPO;
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
public class UnitRepositoryImpl extends ServiceImpl<UnitMapper, UnitPO> implements UnitRepository {

}
