package cc.cloudcoding.dictionary.domain.group.update;

import cc.cloudcoding.dictionary.domain.service.dictionary.Dictionary;
import cc.cloudcoding.dictionary.domain.service.group.Group;
import cc.cloudcoding.dictionary.infrastructure.service.DictionaryRepository;
import cc.cloudcoding.dictionary.model.po.DictionaryPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:07
 */
@Service
public class UpdateGroupEventListener {


    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private ApplicationContext applicationContext;


    @EventListener
    public void handleUpdateGroupEvent(UpdateGroupEvent event) {
        Group group = event.getGroup();

        // 数据库的旧数据
        List<Dictionary> dictionaries = dictionaryRepository.queryListByGroupId(group.getId());
        // 外界来的新数据
        List<Dictionary> items = group.getList();

        // 获得新id集
        List<Long> newIds = items.stream()
                .map(Dictionary::getId)
                .collect(Collectors.toList());
        // 获得旧id集
        List<Long> oldIds = dictionaries.stream()
                .map(Dictionary::getId)
                .collect(Collectors.toList());

        // 1. 删除数据
        List<Long> deleteData = oldIds.stream()
                .filter(element -> !newIds.contains(element))
                .collect(Collectors.toList());
        dictionaryRepository.removeByIds(deleteData);

        // 2. 新增数据
        List<Dictionary> addData = items.stream()
                .filter(element -> !oldIds.contains(element.getId()))
                .collect(Collectors.toList());
        dictionaryRepository.saveBatch(addData.stream().map(item->{
            DictionaryPO po = new DictionaryPO();
            po.setGroupId(item.getGroupId());
            po.setLabel(item.getLabel());
            po.setValue(item.getValue());
            po.setId(item.getId());
            return po;
        }).collect(Collectors.toList()));

        // 3. 更新数据
        List<Dictionary> updateData = items.stream()
                .filter(item -> dictionaries.stream().anyMatch(dict -> Objects.equals(dict.getId(), item.getId())))
                .collect(Collectors.toList());
        dictionaryRepository.updateBatchById(updateData.stream().map(item->{
            DictionaryPO po = new DictionaryPO();
            po.setGroupId(item.getGroupId());
            po.setLabel(item.getLabel());
            po.setValue(item.getValue());
            po.setId(item.getId());
            return po;
        }).collect(Collectors.toList()));
    }
}
