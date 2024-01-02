package cn.edu.hcnu.dictionary.domain.group.create;

import cn.edu.hcnu.dictionary.domain.service.dictionary.Dictionary;
import cn.edu.hcnu.dictionary.infrastructure.service.DictionaryRepository;
import cn.edu.hcnu.dictionary.model.po.DictionaryPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CreateGroupEventListener {


    @Autowired
    private DictionaryRepository dictionaryRepository;


    @EventListener
    public void handleCreateGroupEvent(CreateGroupEvent event) {


        List<Dictionary> updatedDictionaries = event.getGroup().getList().stream()
                .peek(dictionary -> dictionary.setGroupId(event.getGroup().getId()))
                .collect(Collectors.toList());

        dictionaryRepository.saveBatch(updatedDictionaries.stream().map(item->{
            DictionaryPO po = new DictionaryPO();
            po.setGroupId(item.getGroupId());
            po.setLabel(item.getLabel());
            po.setValue(item.getValue());
            po.setId(item.getId());
            return po;
        }).collect(Collectors.toList()));
    }


}
