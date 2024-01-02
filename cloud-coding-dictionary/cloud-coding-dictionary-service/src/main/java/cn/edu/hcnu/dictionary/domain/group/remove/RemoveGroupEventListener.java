package cn.edu.hcnu.dictionary.domain.group.remove;

import cn.edu.hcnu.dictionary.domain.service.dictionary.Dictionary;
import cn.edu.hcnu.dictionary.infrastructure.service.DictionaryRepository;
import cn.edu.hcnu.dictionary.model.po.DictionaryPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:07
 */
@Service
public class RemoveGroupEventListener {


    @Autowired
    private DictionaryRepository dictionaryRepository;

    @EventListener
    public void handleCustomEvent(RemoveGroupEvent event) {
        dictionaryRepository.remove(new LambdaQueryWrapper<DictionaryPO>().eq(DictionaryPO::getGroupId, event.getRemoveGroupId()));
    }
}
