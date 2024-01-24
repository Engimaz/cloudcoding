package cc.cloudcoding.manager.domain.event.url.remove;

import cc.cloudcoding.manager.infrastructure.repository.FeatureUrlRepository;
import cc.cloudcoding.manager.model.po.FeatureUrlPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service

public class RemoveUrlListener {

    @Autowired
    private FeatureUrlRepository featureUrlRepository;

    @EventListener
    public void handleCustomEvent(RemoveUrlEvent event) {
        featureUrlRepository.remove(new LambdaQueryWrapper<FeatureUrlPO>().eq(FeatureUrlPO::getUrlId, event.getUrlId()));
    }
}
