package cn.edu.hcnu.manager.domain.event.url.remove;

import cn.edu.hcnu.manager.infrastructure.repository.FeatureUrlRepository;
import cn.edu.hcnu.manager.model.po.FeatureUrlPO;
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
