package cn.edu.hcnu.course.domain.event.unit.remove;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service

public class RemoveUnitListener {

//    @Autowired
//    private CommentDomainService commentDomainService;

    @EventListener
    public void handleCustomEvent(RemoveUnitEvent event) {


    }
}
