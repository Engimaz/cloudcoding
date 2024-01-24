package cc.cloudcoding.program.infrastructure.repository.impl;

import cc.cloudcoding.program.infrastructure.mapper.FolderMapper;
import cc.cloudcoding.program.model.po.FolderPO;
import cc.cloudcoding.program.infrastructure.repository.FolderRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<FolderPO> getFoldersByParentId(Long id) {

        return baseMapper.selectList(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getParentId, id).ne(FolderPO::getId, id));
    }
}
