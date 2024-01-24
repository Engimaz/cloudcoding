package cc.cloudcoding.manager.domain.service.position;

import cc.cloudcoding.manager.domain.event.organization.OrganizationPublisher;
import cc.cloudcoding.manager.model.po.PositionPO;
import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cc.cloudcoding.manager.infrastructure.repository.PositionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Data

public class Position {
    private Long id;
    private String name;
    private String status;
    private String value;
    private Long organizationId;

    @Autowired
    private PositionRepository positionRepository;

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Autowired
    private OrganizationPublisher organizationPublisher;

    private void save() {
        PositionPO positionPO = new PositionPO();
        positionPO.setId(this.id);
        positionPO.setName(this.name);
        positionPO.setStatus(dictionaryService.getDictionaryByValue(this.status).getId());
        positionPO.setValue(this.value);
        positionPO.setOrganizationId(this.organizationId);
        boolean save = positionRepository.save(positionPO);
        if (!save) {
            throw new RuntimeException("新增职位失败");
        }
    }

    public void update() {
        PositionPO positionPO = new PositionPO();
        positionPO.setId(this.id);
        positionPO.setName(this.name);
        positionPO.setStatus(dictionaryService.getDictionaryByValue(this.status).getId());
        positionPO.setValue(this.value);
        positionPO.setOrganizationId(this.organizationId);
        boolean updateById = positionRepository.updateById(positionPO);
        if (!updateById) {
            throw new RuntimeException("更新职位失败");
        }
    }

    // 删除职位
    public void remove() {
        positionRepository.removeById(id);
    }

}
