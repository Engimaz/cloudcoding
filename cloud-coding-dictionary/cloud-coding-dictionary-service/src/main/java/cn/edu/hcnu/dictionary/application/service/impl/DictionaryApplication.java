package cn.edu.hcnu.dictionary.application.service.impl;

import cn.edu.hcnu.dictionary.application.assembler.DictionaryMapping;
import cn.edu.hcnu.dictionary.application.service.IDictionaryApplication;
import cn.edu.hcnu.dictionary.domain.service.dictionary.Dictionary;
import cn.edu.hcnu.dictionary.model.dto.DictionaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DictionaryApplication implements IDictionaryApplication {

    private final ApplicationContext applicationContext;


    private final DictionaryMapping dictionaryMapping;

    @Override
    public DictionaryDTO getDictionaryByLabel(String label) {
        Dictionary bean = applicationContext.getBean(Dictionary.class);
        bean.setLabel(label);
        bean.renderByLabel();


        return dictionaryMapping.sourceToTarget(bean);
    }

    @Override
    public DictionaryDTO getDictionaryById(Long id) {
        Dictionary bean = applicationContext.getBean(Dictionary.class);
        bean.setId(id);
        bean.render();
        return dictionaryMapping.sourceToTarget(bean);
    }

    @Override
    public DictionaryDTO getDictionaryByValue(String value) {
        Dictionary bean = applicationContext.getBean(Dictionary.class);
        bean.setValue(value);
        bean.renderByValue();
        return dictionaryMapping.sourceToTarget(bean);

    }
}
