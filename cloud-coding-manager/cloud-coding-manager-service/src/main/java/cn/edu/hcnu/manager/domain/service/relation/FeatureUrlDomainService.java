package cn.edu.hcnu.manager.domain.service.relation;

import cn.edu.hcnu.manager.domain.service.relation.factorty.FeatureUrlFactory;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.po.FeatureUrlPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:29
 */
@Component
public class FeatureUrlDomainService {


    @Autowired
    private FeatureUrlRepository featureUrlRepository;
    @Autowired
    private FeatureUrlFactory featureUrlFactory;


    public void removeByUrlId(Long urlId) {
        featureUrlRepository.remove(new LambdaQueryWrapper<FeatureUrlPO>().eq(FeatureUrlPO::getUrlId, urlId));
    }

    public void removeByFeatureId(Long featureId) {
        featureUrlRepository.remove(new LambdaQueryWrapper<FeatureUrlPO>().eq(FeatureUrlPO::getFeatureId, featureId));

    }

    public boolean save(List<FeatureUrl> relations) {
        return featureUrlRepository.saveBatch(featureUrlFactory.createFeatureUrlPO(relations));
    }
}
