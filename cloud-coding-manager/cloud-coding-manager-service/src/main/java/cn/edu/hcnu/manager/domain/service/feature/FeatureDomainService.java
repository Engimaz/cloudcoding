package cn.edu.hcnu.manager.domain.service.feature;


import cn.edu.hcnu.manager.domain.event.feature.FeaturePublisher;
import cn.edu.hcnu.manager.domain.service.feature.factory.FeatureFactory;
import cn.edu.hcnu.manager.domain.service.relation.factorty.FeatureUrlFactory;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureOrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureRepository;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.po.FeatureOrganizationPO;
import cn.edu.hcnu.manager.model.po.FeaturePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能领域服务
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/31
 */

@Component
public class FeatureDomainService {

    private final FeatureFactory featureFactory;

    private final FeatureRepository featureRepository;

    private final FeaturePublisher featurePublisher;


    private final FeatureUrlRepository featureUrlRepository;

    private final FeatureUrlFactory featureUrlFactory;

    @Autowired
    private FeatureOrganizationRepository featureOrganizationRepository;

    public FeatureDomainService(FeatureFactory featureFactory, FeatureRepository featureRepository, FeaturePublisher featurePublisher, FeatureUrlRepository featureUrlRepository, FeatureUrlFactory featureUrlFactory) {
        this.featureFactory = featureFactory;
        this.featureRepository = featureRepository;
        this.featurePublisher = featurePublisher;
        this.featureUrlRepository = featureUrlRepository;
        this.featureUrlFactory = featureUrlFactory;
    }

    /**
     * @param feature 功能领域对象
     * @description: 添加功能
     * @return: cn.edu.hcnu.manager.domain.service.feature.Feature
     * @author: 不才小马
     * @time: 2023/9/12 13:21
     */
    public Feature save(Feature feature) {
        FeaturePO featurePO = featureFactory.createFeaturePO(feature);
        // 保存本体
        boolean save = featureRepository.save(featurePO);
        if (save) {
            feature.setId(featurePO.getId());
            // 发布
            featurePublisher.publishAddFeatureEvent(feature);
            return featureFactory.createFeature(featurePO);
        }


        return null;
    }

    /**
     * @param id 功能id
     * @description: 删除功能
     * @return: java.lang.Boolean
     * @author: Administrator
     * @time: 2023/9/12 14:46
     */
    public Boolean delete(Long id) {
        if (featureRepository.removeById(id)) {
            featurePublisher.publishRemoveFeatureEvent(id);
            return true;
        }
        return false;
    }

    /**
     * @param feature 功能领域对象
     * @description: 更新功能
     * @return: cn.edu.hcnu.manager.domain.service.feature.Feature
     * @author: Administrator
     * @time: 2023/9/12 14:45
     */
    public Feature update(Feature feature) {
        FeaturePO featurePO = featureFactory.createFeaturePO(feature);
        // 保存本体
        boolean save = featureRepository.updateById(featurePO);

        if (save) {
            featurePublisher.publishUpdateFeatureEvent(feature);
            return featureFactory.createFeature(featurePO);
        }
        return null;
    }

    /**
     * @param id 功能id
     * @description: 查找功能
     * @return: cn.edu.hcnu.manager.domain.service.feature.Feature
     * @author: Administrator
     * @time: 2023/9/12 14:46
     */
    public Feature findById(Long id) {
        FeaturePO byId = featureRepository.getById(id);
        return featureFactory.createFeature(byId);
    }

    public List<Feature> list(Integer page, Integer size, String keyword) {
        List<Feature> collect = this.pageQuery(page, size, keyword).getRecords().stream().map((f) -> featureFactory.createFeature(f)).collect(Collectors.toList());
        return collect;
    }


    public Long count(Integer page, Integer size, String keyword) {
        return this.pageQuery(page, size, keyword).getTotal();
    }

    private Page<FeaturePO> pageQuery(Integer page, Integer size, String keyword) {
        Page<FeaturePO> p1 = new Page<>(page, size);
        LambdaQueryWrapper<FeaturePO> wrapper = new LambdaQueryWrapper<FeaturePO>().like(keyword != null, FeaturePO::getName, keyword);
        return featureRepository.getBaseMapper().selectPage(p1, wrapper);
    }

    public List<Feature> queryByOrganizationId(Long id) {
        List<FeatureOrganizationPO> list = featureOrganizationRepository
                .list(new LambdaQueryWrapper<FeatureOrganizationPO>().eq(FeatureOrganizationPO::getOrganizationId, id));
        if (list != null && list.size() > 0) {
            return featureFactory.createFeature(list.stream().map((f) -> featureRepository.getById(f.getFeatureId())).collect(Collectors.toList()));
        }
        return null;
    }
}
