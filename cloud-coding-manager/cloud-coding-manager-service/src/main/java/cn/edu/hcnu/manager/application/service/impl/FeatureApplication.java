package cn.edu.hcnu.manager.application.service.impl;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.manager.application.assembler.AddFeatureCommandToFeatureMapping;
import cn.edu.hcnu.manager.application.assembler.FeatureToDTOMapping;
import cn.edu.hcnu.manager.application.assembler.UpdateFeatureCommandToFeatureMapping;
import cn.edu.hcnu.manager.application.service.IFeatureApplication;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.domain.service.feature.FeatureDomainService;
import cn.edu.hcnu.manager.model.command.AddFeatureCommand;
import cn.edu.hcnu.manager.model.command.UpdateFeatureCommand;
import cn.edu.hcnu.manager.model.dto.FeatureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 14:56
 */
@Component
public class FeatureApplication implements IFeatureApplication {

    @Autowired
    private FeatureDomainService featureDomainService;

    @Autowired
    private FeatureToDTOMapping featureToDTOMapping;

    @Autowired
    private AddFeatureCommandToFeatureMapping addFeatureCommandToFeatureMapping;

    @Autowired
    private UpdateFeatureCommandToFeatureMapping updateFeatureCommandToFeatureMapping;

    @Override
    public FeatureDTO queryById(Long id) {
        Feature byId = featureDomainService.findById(id);
        return featureToDTOMapping.sourceToTarget(byId);
    }

    @Override
    public Boolean deleteById(Long id) {
        return featureDomainService.delete(id);
    }

    @Override
    public FeatureDTO addFeature(AddFeatureCommand addFeatureCommand) {
        Feature save = featureDomainService.save(addFeatureCommandToFeatureMapping.sourceToTarget(addFeatureCommand));
        return featureToDTOMapping.sourceToTarget(save);
    }

    @Override
    public FeatureDTO updateFeature(UpdateFeatureCommand updateFeatureCommand) {
        Feature update = featureDomainService.update(updateFeatureCommandToFeatureMapping.sourceToTarget(updateFeatureCommand));
        return featureToDTOMapping.sourceToTarget(update);
    }

    @Override
    public PageDTO<FeatureDTO, CommonQuery> list(CommonQuery commonQuery) {
        List<Feature> list = featureDomainService.list(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword());
        List<FeatureDTO> list1 = featureToDTOMapping.sourceToTarget(list);
        Long count = featureDomainService.count(commonQuery.getPage(), commonQuery.getSize(), commonQuery.getKeyword());
        return new PageDTO<>(list1, count, commonQuery);
    }
}
