package cc.cloudcoding.program.infrastructure.repository.impl;

import cc.cloudcoding.program.infrastructure.mapper.ProgramUserMapper;
import cc.cloudcoding.program.model.po.ProgramUserPO;
import cc.cloudcoding.program.infrastructure.repository.ProgramUserRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-10-12 12:11:09
 */
@Service
public class ProgramUserRepositoryImpl extends ServiceImpl<ProgramUserMapper, ProgramUserPO> implements ProgramUserRepository {

}
