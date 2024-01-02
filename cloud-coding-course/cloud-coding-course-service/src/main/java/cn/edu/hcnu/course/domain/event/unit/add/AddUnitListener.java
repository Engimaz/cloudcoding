package cn.edu.hcnu.course.domain.event.unit.add;

import cn.edu.hcnu.course.domain.service.node.NodeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service
public class AddUnitListener {

    @Autowired
    private NodeDomainService domainService;

    @EventListener
    public void handleCustomEvent(AddUnitEvent event) {
        Optional.ofNullable(event.getUnit().getNodes()).ifPresent(nodes ->
                event.getUnit().setNodes(nodes.stream().map(node -> {
                    node.setUnitId(event.getUnit().getId());
                    return domainService.save(node);
                }).collect(Collectors.toList()))
        );

    }
}
