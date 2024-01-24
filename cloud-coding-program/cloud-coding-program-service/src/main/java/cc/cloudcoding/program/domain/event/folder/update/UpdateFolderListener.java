package cc.cloudcoding.program.domain.event.folder.update;

import cc.cloudcoding.program.domain.service.file.File;
import cc.cloudcoding.program.domain.service.folder.Folder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateFolderListener {


    private final ApplicationContext applicationContext;


    @EventListener
    public void handleCustomEvent(UpdateFolderEvent event) {

        if (event.getFolder() == null) {
            return;
        }

        // 获得数据库旧数据
        Folder bean = applicationContext.getBean(Folder.class);
        bean.setId(event.getFolder().getId());
        bean.render();

        List<Folder> oldFolders = bean.getFolders();
        List<Folder> newFolders = event.getFolder().getFolders();

        // 新增数据
        newFolders.stream()
                .filter(newFolder -> oldFolders.stream().noneMatch(oldFolderItem -> Objects.equals(newFolder.getId(), oldFolderItem.getId())))
                .forEach(Folder::save);

        // 删除数据
        oldFolders.stream()
                .filter(oldFolder -> newFolders.stream().noneMatch(newFolder -> Objects.equals(newFolder.getId(), oldFolder.getId())))
                .forEach(Folder::remove);

        // 更新数据
        oldFolders.stream()
                .filter(oldFolder -> newFolders.stream().anyMatch(newFolder -> Objects.equals(newFolder.getId(), oldFolder.getId())))
                .forEach(Folder::update);


        List<File> oldFiles = bean.getFiles();
        List<File> newFiles = event.getFolder().getFiles();

        // 新增数据
        newFiles.stream()
                .filter(newFile -> oldFiles.stream().noneMatch(oldFile -> Objects.equals(newFile.getId(), oldFile.getId())))
                .forEach(File::save);

        // 删除数据
        oldFiles.stream()
                .filter(oldFile -> newFiles.stream().noneMatch(newFile -> Objects.equals(newFile.getId(), oldFile.getId())))
                .forEach(File::remove);

        // 更新数据
        oldFiles.stream()
                .filter(oldFile -> newFiles.stream().anyMatch(newFile -> Objects.equals(newFile.getId(), oldFile.getId())))
                .forEach(File::update);
    }
}
