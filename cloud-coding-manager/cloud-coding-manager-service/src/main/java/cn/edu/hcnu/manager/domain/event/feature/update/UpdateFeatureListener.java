package cn.edu.hcnu.manager.domain.event.feature.update;

import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.po.FeatureUrlPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UpdateFeatureListener {

    @Autowired
    private FeatureUrlRepository featureUrlRepository;


    @EventListener
    public void handleCustomEvent(UpdateFeatureEvent event) {
        // 移除功能与url的关系
        featureUrlRepository.remove(new LambdaQueryWrapper<FeatureUrlPO>().eq(FeatureUrlPO::getFeatureId, event.getFeature().getId()));
        // 添加功能与url的关系
        Optional.ofNullable(event.getFeature().getUrls()).ifPresent(urls -> {
            List<FeatureUrlPO> collect = urls.stream().map(item -> {
                FeatureUrlPO featureUrlPo = new FeatureUrlPO();
                featureUrlPo.setFeatureId(event.getFeature().getId());
                featureUrlPo.setUrlId(Long.valueOf(item));
                return featureUrlPo;
            }).collect(Collectors.toList());
            featureUrlRepository.saveBatch(collect);
        });


    }
}
