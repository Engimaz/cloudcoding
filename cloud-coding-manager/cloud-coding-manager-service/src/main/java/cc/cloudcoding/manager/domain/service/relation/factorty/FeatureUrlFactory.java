package cc.cloudcoding.manager.domain.service.relation.factorty;

import cc.cloudcoding.id.domain.service.IDGenerator;
import cc.cloudcoding.manager.model.po.FeatureUrlPO;
import cc.cloudcoding.manager.domain.service.relation.FeatureUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 14:35
 */
@Component
public class FeatureUrlFactory {


    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;


    public FeatureUrl createFeatureUrl(FeatureUrlPO featureUrlPO) {
        FeatureUrl featureUrl = new FeatureUrl();
        featureUrl.setId(featureUrlPO.getId());
        featureUrl.setFeatureId(featureUrlPO.getFeatureId());
        featureUrl.setUrlId(featureUrlPO.getUrlId());
        return featureUrl;
    }

    public FeatureUrlPO createFeatureUrlPO(FeatureUrl featureUrl) {
        FeatureUrlPO featureUrlPO = new FeatureUrlPO();
        if (featureUrlPO.getId() != null) {
            featureUrl.setId(featureUrlPO.getId());
        } else {
            featureUrl.setId(Long.valueOf(idGenerator.nextID()));
        }
        featureUrlPO.setFeatureId(featureUrl.getFeatureId());
        featureUrlPO.setUrlId(featureUrl.getUrlId());
        return featureUrlPO;
    }


    public Collection<FeatureUrlPO> createFeatureUrlPO(List<FeatureUrl> relations) {
        return relations.stream().map(this::createFeatureUrlPO).collect(Collectors.toList());
    }
}
