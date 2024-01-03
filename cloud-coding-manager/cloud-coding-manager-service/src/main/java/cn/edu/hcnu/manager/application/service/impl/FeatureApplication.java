package cn.edu.hcnu.manager.application.service.impl;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.application.assembler.FeatureToDTOMapping;
import cn.edu.hcnu.manager.application.service.IFeatureApplication;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureRepository;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.command.AddFeatureCommand;
import cn.edu.hcnu.manager.model.command.UpdateFeatureCommand;
import cn.edu.hcnu.manager.model.dto.FeatureDTO;
import cn.edu.hcnu.manager.model.po.FeaturePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 14:56
 */
@Component
public class FeatureApplication implements IFeatureApplication {


    @Autowired
    private FeatureToDTOMapping featureToDTOMapping;


    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Qualifier("snowflake")
    @Autowired
    private IDGenerator idGenerator;

    @Override
    public FeatureDTO queryById(Long id) {
        Feature bean = applicationContext.getBean(Feature.class);
        bean.setId(id);
        bean.render();
        return featureToDTOMapping.sourceToTarget(bean);
    }

    @Override
    public Boolean deleteById(Long id) {
        Feature bean = applicationContext.getBean(Feature.class);
        bean.setId(id);
        bean.delete();
        return true;
    }

    @Override
    public FeatureDTO addFeature(AddFeatureCommand addFeatureCommand) {
        Feature bean = applicationContext.getBean(Feature.class);
        bean.setId(Long.valueOf(idGenerator.nextID()));
        bean.setName(addFeatureCommand.getName());
        bean.setValue(addFeatureCommand.getValue());
        bean.setStatus(addFeatureCommand.getStatus());
        bean.setDescription(addFeatureCommand.getDescription());
        bean.setUrls(addFeatureCommand.getUrls());
        bean.save();
        return featureToDTOMapping.sourceToTarget(bean);
    }

    @Override
    public FeatureDTO updateFeature(UpdateFeatureCommand command) {
        Feature bean = applicationContext.getBean(Feature.class);
        bean.setId(command.getId());
        bean.setName(command.getName());
        bean.setValue(command.getValue());
        bean.setDescription(command.getDescription());
        bean.setUrls(command.getUrls());
        bean.update();
        return featureToDTOMapping.sourceToTarget(bean);
    }

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Autowired
    private FeatureUrlRepository featureUrlRepository;

    @Override
    public PageDTO<FeatureDTO, CommonQuery> list(CommonQuery commonQuery) {
        Page<FeaturePO> page = new Page<>(commonQuery.getPage(), commonQuery.getSize());

        LambdaQueryWrapper<FeaturePO> featurePOLambdaQueryWrapper = new LambdaQueryWrapper<>();

        Page<FeaturePO> res = featureRepository.page(page, featurePOLambdaQueryWrapper);

        List<Feature> collect = res.getRecords().stream().map(po -> {
            Feature bean = applicationContext.getBean(Feature.class);
            bean.setId(po.getId());
            bean.setName(po.getName());
            bean.setStatus(dictionaryService.getDictionaryById(po.getStatus()).getValue());
            bean.setDescription(po.getDescription());
            bean.setValue(po.getValue());
            bean.setUrls(featureUrlRepository.queryByFeatureId(po.getId()).stream().map(item -> item.getUrlId().toString()).collect(Collectors.toList()));
            return bean;
        }).collect(Collectors.toList());
        return new PageDTO<>(featureToDTOMapping.sourceToTarget(collect), res.getTotal(), commonQuery);
    }

    @Override
    public PageDTO<FeatureDTO, CommonQuery> all() {
        LambdaQueryWrapper<FeaturePO> f = new LambdaQueryWrapper<>();
        List<FeaturePO> res = featureRepository.list(f);

        List<Feature> collect = res.stream().map(po -> {
            Feature bean = applicationContext.getBean(Feature.class);
            bean.setId(po.getId());
            bean.setName(po.getName());
            bean.setStatus(dictionaryService.getDictionaryById(po.getStatus()).getValue());
            bean.setDescription(po.getDescription());
            bean.setValue(po.getValue());
            bean.setUrls(featureUrlRepository.queryByFeatureId(po.getId()).stream().map(item -> item.getUrlId().toString()).collect(Collectors.toList()));
            return bean;
        }).collect(Collectors.toList());
        return new PageDTO<>(featureToDTOMapping.sourceToTarget(collect), (long) res.size(), null);
    }
}
