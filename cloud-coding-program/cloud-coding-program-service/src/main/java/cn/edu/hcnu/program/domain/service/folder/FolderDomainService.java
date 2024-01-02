package cn.edu.hcnu.program.domain.service.folder;

import cn.edu.hcnu.program.domain.event.folder.FolderPublisher;
import cn.edu.hcnu.program.domain.service.folder.factory.FolderFactory;
import cn.edu.hcnu.program.infrastructure.repository.FolderRepository;
import cn.edu.hcnu.program.model.po.FolderPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FolderDomainService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FolderFactory folderFactory;

    @Autowired
    private FolderPublisher folderPublisher;

    public Folder save(Folder folder) {
        FolderPO folderPo = folderFactory.createFolderPo(folder);
        boolean save = folderRepository.save(folderPo);
        if (save) {
            return folderFactory.createFolder(folderPo);
        }
        return null;
    }

    public boolean remove(String id) {
        boolean b = folderRepository.removeById(id);
        if (b) {
            // 发布删除文件夹事件 使得子文件删除
            folderPublisher.pushliRemoveEvent(id);
        }
        return b;
    }

    /**
     * @param id
     * @description: 查找项目顶层文件夹
     * @return: cn.edu.hcnu.program.domain.service.folder.Folder
     * @author: Administrator
     * @time: 2023/10/14 12:20
     */

    public Folder queryTopFolderByProgramId(String id) {
        return folderFactory.createFolder(Objects.requireNonNull(Objects.requireNonNull(folderRepository.list(new LambdaQueryWrapper<FolderPO>()
                                .eq(FolderPO::getProgramId, id))
                        .stream()
                        .filter(f -> Objects.equals(f.getId(), f.getParentId())))
                .findFirst().orElse(null)))
                ;
    }

    public List<Folder> queryChildrenFolderById(String id) {
        return folderFactory.createFolder(folderRepository.list(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getParentId, id).ne(FolderPO::getId, id)));

    }

    public Folder update(Folder folder) {
        FolderPO folderPo = folderFactory.createFolderPo(folder);
        boolean b = folderRepository.updateById(folderPo);
        if (b) {
            return folderFactory.createFolder(folderPo);
        }
        return null;
    }


    public boolean removeProgramFolderByProgramId(Long programId) {
        // 属于这个项目的所有文件夹
        List<FolderPO> list = folderRepository.list(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getProgramId, programId));
        // 删除文件夹
        long count = list.stream().map(FolderPO::getId).distinct().map(String::valueOf).filter(this::remove).map(res -> 1).count();

        return list.size() == count;
    }

    public Folder queryById(String id) {
        return folderFactory.createFolder(folderRepository.getById(id));
    }

    public List<Folder> queryAllFolderByProgramId(String id) {
        return folderFactory.createFolder(folderRepository.list(new LambdaQueryWrapper<FolderPO>().eq(FolderPO::getProgramId, id)));
    }
}
