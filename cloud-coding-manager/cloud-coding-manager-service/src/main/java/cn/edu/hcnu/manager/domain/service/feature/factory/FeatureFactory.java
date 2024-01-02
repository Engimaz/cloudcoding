package cn.edu.hcnu.manager.domain.service.feature.factory;

import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.domain.service.relation.FeatureUrl;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.po.FeaturePO;
import cn.edu.hcnu.manager.model.po.FeatureUrlPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:10
 */
@Component
public class FeatureFactory {

    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Autowired
    private FeatureUrlRepository featureUrlRepository;


    /**
     * @param feature
     * @description: 根据系统领域对象创建数据库对象
     * @return: cn.edu.hcnu.manager.model.po.FeaturePO
     * @author: 不才小马
     * @time: 2023/9/12 13:16
     */
    public FeaturePO createFeaturePO(Feature feature) {
        FeaturePO featurePO = new FeaturePO();
        featurePO.setName(feature.getName());
        if (feature.getId() != null) {
            featurePO.setId(feature.getId());
        } else {
            featurePO.setId(Long.valueOf(idGenerator.nextID()));
        }
        if (feature.getStatus() == null) {
            featurePO.setStatus(Long.valueOf(dictionaryService.getDictionaryByLabel("功能正常").getId()));
        } else {
            featurePO.setStatus(dictionaryService.getDictionaryByLabel(feature.getStatus()).getId());
        }
        return featurePO;
    }


    public Feature createFeature(FeaturePO featurePO) {
        Feature feature = new Feature();
        feature.setId(featurePO.getId());
        feature.setName(featurePO.getName());
        feature.setStatus(dictionaryService.getDictionaryById(featurePO.getStatus()).getLabel());
        feature.setRelations(featureUrlRepository.list(new LambdaQueryWrapper<FeatureUrlPO>().eq(FeatureUrlPO::getFeatureId, featurePO.getId())).stream().map(featureUrlPO -> new FeatureUrl(featureUrlPO.getId(), featurePO.getId(), featureUrlPO.getUrlId())).collect(Collectors.toList()));
        return feature;
    }

    public List<Feature> createFeature(List<FeaturePO> list) {
        return list.stream().map(this::createFeature).collect(Collectors.toList());
    }
}
