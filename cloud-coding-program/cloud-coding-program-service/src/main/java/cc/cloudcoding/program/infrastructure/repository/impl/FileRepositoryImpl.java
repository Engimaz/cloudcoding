package cc.cloudcoding.program.infrastructure.repository.impl;


import cc.cloudcoding.program.infrastructure.mapper.FileMapper;
import cc.cloudcoding.program.model.po.FilePO;
import cc.cloudcoding.program.infrastructure.repository.FileRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-10-12 12:11:08
 */
@Service
public class FileRepositoryImpl extends ServiceImpl<FileMapper, FilePO> implements FileRepository {

}
