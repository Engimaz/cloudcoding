package cc.cloudcoding.course.domain.service.node;

import cc.cloudcoding.course.domain.service.node.factory.NodeFactory;
import cc.cloudcoding.course.infrastructure.repository.NodeRepository;
import cc.cloudcoding.course.model.po.NodePO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeDomainService {
    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private NodeFactory nodeFactory;

    public Node save(Node node) {
        NodePO nodePO = nodeFactory.createNodePO(node);
        if (nodeRepository.save(nodePO)) {
            return nodeFactory.createNode(nodePO);
        }
        return null;
    }

    public boolean delete(Long id) {
        return nodeRepository.removeById(id);
    }

    public List<Node> listByUnitId(String unitId) {
        return nodeFactory.createNode(nodeRepository.list(new LambdaQueryWrapper<NodePO>().eq(NodePO::getUnitId, unitId)));
    }

    public Node getById(String id) {
        return nodeFactory.createNode(nodeRepository.getById(id));
    }

}
