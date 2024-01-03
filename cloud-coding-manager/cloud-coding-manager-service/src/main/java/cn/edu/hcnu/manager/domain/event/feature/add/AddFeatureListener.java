package cn.edu.hcnu.manager.domain.event.feature.add;

import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.po.FeatureUrlPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddFeatureListener {

    @Autowired
    private FeatureUrlRepository featureUrlRepository;



    @EventListener
    public void handleCustomEvent(AddFeatureEvent event) {

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
