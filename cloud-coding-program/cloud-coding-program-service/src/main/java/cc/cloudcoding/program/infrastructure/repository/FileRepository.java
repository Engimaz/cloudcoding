package cc.cloudcoding.program.infrastructure.repository;

import cc.cloudcoding.program.model.po.FilePO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author aichen
 * @since 2023-10-12 12:11:08
 */
public interface FileRepository extends IService<FilePO> {

    List<FilePO> getFilesByFolderId(Long id);

    void removeFileByFolderId(Long id);
}
