package cc.cloudcoding.manager.domain.service.relation.factorty;

import cc.cloudcoding.id.domain.service.IDGenerator;
import cc.cloudcoding.manager.model.po.FeatureOrganizationPO;
import cc.cloudcoding.manager.domain.service.relation.FeatureOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeatureOrganizationFactory {

    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;

    public FeatureOrganizationPO createFeatureOrganizationPO(FeatureOrganization fo) {
        FeatureOrganizationPO featureOrganizationPO = new FeatureOrganizationPO();
        if (fo.getId() == null) {
            featureOrganizationPO.setId(Long.valueOf(idGenerator.nextID()));
        }
        featureOrganizationPO.setFeatureId(fo.getFeatureId());
        featureOrganizationPO.setOrganizationId(fo.getOrganizationId());
        return featureOrganizationPO;
    }

    public FeatureOrganization createFeatureOrganization(FeatureOrganizationPO po) {
        FeatureOrganization featureOrganization = new FeatureOrganization();
        featureOrganization.setFeatureId(po.getFeatureId());
        featureOrganization.setOrganizationId(po.getOrganizationId());
        featureOrganization.setId(po.getId());
        return featureOrganization;
    }

    public Collection<FeatureOrganizationPO> createFeatureOrganizationPO(List<FeatureOrganization> collect) {
        return collect.stream().map(this::createFeatureOrganizationPO).collect(Collectors.toList());
    }
}
