package cc.cloudcoding.course.domain.service.node.factory;

import cc.cloudcoding.course.domain.service.node.Node;
import cc.cloudcoding.course.model.po.NodePO;
import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cc.cloudcoding.id.domain.service.IDGenerator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NodeFactory {
    @Autowired
    @Qualifier("snowflake")
    private IDGenerator idGenerator;


    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    public Node createNode(NodePO po) {
        Node node = new Node();
        node.setId(String.valueOf(po.getId()));
        node.setUrl(po.getUrl());
        node.setType(dictionaryService.getDictionaryById(po.getType()).getLabel());
        node.setDescription(po.getDescription());
        node.setName(po.getName());
        node.setUnitId(String.valueOf(po.getUnitId()));
        return node;
    }

    public NodePO createNodePO(Node node) {
        NodePO nodePO = new NodePO();
        if (node.getId() == null) {
            nodePO.setId(Long.valueOf(idGenerator.nextID()));
        } else {
            nodePO.setId(Long.valueOf(node.getId()));
        }
        nodePO.setType(dictionaryService.getDictionaryByLabel(node.getType()).getId());
        nodePO.setName(node.getName());
        nodePO.setDescription(node.getDescription());
        nodePO.setUnitId(Long.valueOf(node.getUnitId()));
        nodePO.setUrl(node.getUrl());
        return nodePO;
    }

    public List<Node> createNode(List<NodePO> list) {
        return list.stream().map(this::createNode).collect(Collectors.toList());
    }
}
