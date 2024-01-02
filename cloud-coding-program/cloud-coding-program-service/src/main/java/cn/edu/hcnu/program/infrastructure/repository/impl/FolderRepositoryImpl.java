package cn.edu.hcnu.program.infrastructure.repository.impl;

import cn.edu.hcnu.program.infrastructure.mapper.FolderMapper;
import cn.edu.hcnu.program.infrastructure.repository.FolderRepository;
import cn.edu.hcnu.program.model.po.FolderPO;
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
public class FolderRepositoryImpl extends ServiceImpl<FolderMapper, FolderPO> implements FolderRepository {

}
