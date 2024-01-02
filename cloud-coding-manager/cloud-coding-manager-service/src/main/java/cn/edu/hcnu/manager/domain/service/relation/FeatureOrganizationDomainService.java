package cn.edu.hcnu.manager.domain.service.relation;

import cn.edu.hcnu.manager.domain.service.relation.factorty.FeatureOrganizationFactory;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureOrganizationRepository;
import cn.edu.hcnu.manager.model.po.FeatureOrganizationPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeatureOrganizationDomainService {

    @Autowired
    private FeatureOrganizationRepository featureOrganizationRepository;

    @Autowired
    private FeatureOrganizationFactory featureOrganizationFactory;

    public boolean removeByFeatureId(Long featureId) {
        return featureOrganizationRepository.remove(new LambdaQueryWrapper<FeatureOrganizationPO>().eq(FeatureOrganizationPO::getFeatureId, featureId));
    }

    public boolean removeByOrganizationId(Long id) {
        return featureOrganizationRepository.remove(new LambdaQueryWrapper<FeatureOrganizationPO>().eq(FeatureOrganizationPO::getOrganizationId, id));

    }


    public boolean batchSave(List<FeatureOrganization> collect) {
        return featureOrganizationRepository.saveBatch(featureOrganizationFactory.createFeatureOrganizationPO(collect));
    }

    public List<FeatureOrganization> queryByOrganizationId(Long id) {
        return featureOrganizationRepository.list(new LambdaQueryWrapper<FeatureOrganizationPO>().eq(FeatureOrganizationPO::getOrganizationId, id)
        ).stream().map(m -> featureOrganizationFactory.createFeatureOrganization(m)).collect(Collectors.toList());

    }
}
