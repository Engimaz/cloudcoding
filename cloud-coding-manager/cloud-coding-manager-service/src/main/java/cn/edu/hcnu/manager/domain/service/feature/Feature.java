package cn.edu.hcnu.manager.domain.service.feature;

import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.manager.domain.event.feature.FeaturePublisher;
import cn.edu.hcnu.manager.domain.service.relation.FeatureUrl;
import cn.edu.hcnu.manager.domain.service.relation.factorty.FeatureUrlFactory;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureOrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureRepository;
import cn.edu.hcnu.manager.model.po.FeaturePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 功能领域
 * @author: Administrator
 * @time: 2023/9/12 13:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope("prototype")
public class Feature {
    private Long id;
    private String name;
    private String status;
    private String value;
    private String description;
    private List<String> urls;

    @Autowired
    private FeatureUrlFactory featureUrlFactory;
    @Autowired
    private FeaturePublisher featurePublisher;
    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureOrganizationRepository featureOrganizationRepository;
    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    public void save() {
        FeaturePO po = new FeaturePO();
        po.setStatus(dictionaryService.getDictionaryByValue(this.status).getId());
        po.setName(this.name);
        po.setValue(this.value);
        po.setDescription(this.description);
        po.setId(this.id);
        // 保存本体
        boolean save = featureRepository.save(po);
        if (!save) {

            return;
            // 发布
        }
        featurePublisher.publishAddFeatureEvent(this);


    }


    public void delete() {
        if (featureRepository.removeById(id)) {
            featurePublisher.publishRemoveFeatureEvent(id);
        }
    }


    public void update( ) {
        FeaturePO po = new FeaturePO();
        po.setStatus(dictionaryService.getDictionaryByValue(this.status).getId());
        po.setName(this.name);
        po.setValue(this.value);
        po.setDescription(this.description);
        po.setId(this.id);
        boolean b = featureRepository.updateById(po);
        if (b) {
            featurePublisher.publishUpdateFeatureEvent(this);
        }
    }

    public void render() {
        FeaturePO byId = featureRepository.getById(this.id);
        this.setId(byId.getId());
        this.setName(byId.getName());
        this.setValue(byId.getValue());
        this.setDescription(byId.getDescription());
        this.setStatus(dictionaryService.getDictionaryById(byId.getStatus()).getValue());
    }
}
