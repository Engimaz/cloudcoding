package cc.cloudcoding.manager.domain.service.feature;

import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cc.cloudcoding.manager.domain.event.feature.FeaturePublisher;
import cc.cloudcoding.manager.domain.service.relation.factorty.FeatureUrlFactory;
import cc.cloudcoding.manager.infrastructure.repository.FeatureOrganizationRepository;
import cc.cloudcoding.manager.infrastructure.repository.FeatureRepository;
import cc.cloudcoding.manager.model.po.FeaturePO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 功能领域
 * @author: Administrator
 * @time: 2023/9/12 13:07
 */
@Data
@RequiredArgsConstructor
@Component
@Scope("prototype")
public class Feature {
    private Long id;
    private String name;
    private String status;
    private String value;
    private String description;
    private List<String> urls;

    private final FeatureUrlFactory featureUrlFactory;
    private final FeaturePublisher featurePublisher;
    private final FeatureRepository featureRepository;

    private final FeatureOrganizationRepository featureOrganizationRepository;

    @DubboReference(group = "dictionary")    @Lazy
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


    public void update() {
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
