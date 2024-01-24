package cc.cloudcoding.dictionary.interfaces;

import cc.cloudcoding.dictionary.model.dto.DictionaryDTO;
import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cc.cloudcoding.dictionary.application.service.IDictionaryApplication;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 对内部的微服务提供
 * @author: Administrator
 * @time: 2023/8/13 23:38
 */

@DubboService(group = "dictionary")
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {


    @Autowired
    private IDictionaryApplication dictionaryApplication;

    @Override
    public DictionaryDTO getDictionaryByLabel(String label) {
        return dictionaryApplication.getDictionaryByLabel(label);
    }

    @Override
    public DictionaryDTO getDictionaryByValue(String value) {
        return dictionaryApplication.getDictionaryByValue(value);
    }

    @Override
    public DictionaryDTO getDictionaryById(Long id) {
        return dictionaryApplication.getDictionaryById(id);
    }


}
