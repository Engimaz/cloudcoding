package cn.edu.hcnu.program.infrastructure.repository.impl;

import cn.edu.hcnu.program.infrastructure.mapper.ProgramUserMapper;
import cn.edu.hcnu.program.infrastructure.repository.ProgramUserRepository;
import cn.edu.hcnu.program.model.po.ProgramUserPO;
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
