package cn.edu.hcnu.manager.domain.service.organization;

import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.manager.domain.event.organization.OrganizationPublisher;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.domain.service.position.Position;
import cn.edu.hcnu.manager.domain.service.relation.OrganizationRecord;
import cn.edu.hcnu.manager.domain.service.relation.UserPosition;
import cn.edu.hcnu.manager.infrastructure.repository.OrganizationRepository;
import cn.edu.hcnu.manager.model.po.OrganizationPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/13 14:09
 */

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    private Long id;
    private String name;
    private String status;
    private String avatar;
    private String img;
    private String description;
    private String address;
    private String location;
    private String type;
    // 组织的职位
    private List<Position> positions;
    // 组织的功能
    private List<Feature> features;
    // 组织的用户关系
    private List<UserPosition> userPositions;

    @Autowired
    private OrganizationRepository organizationRepository;

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Autowired
    private OrganizationPublisher organizationPublisher;

    public void save() {
        OrganizationPO p = new OrganizationPO();
        p.setId(this.id);
        p.setName(this.name);
        p.setStatus(dictionaryService.getDictionaryByValue(this.status).getId());
        p.setAvatar(this.avatar);
        p.setImg(this.img);
        p.setDescription(this.description);
        p.setAddress(this.address);
        p.setLocation(this.location);
        p.setType(dictionaryService.getDictionaryByValue(this.type).getId());
        boolean save = organizationRepository.save(p);
        if (save) {
            organizationPublisher.publishAddOrganizationEvent(this);
        }
    }

    public void update() {
        OrganizationPO p = new OrganizationPO();
        p.setId(this.id);
        p.setName(this.name);
        p.setStatus(dictionaryService.getDictionaryByValue(this.status).getId());
        p.setAvatar(this.avatar);
        p.setImg(this.img);
        p.setDescription(this.description);
        p.setAddress(this.address);
        p.setLocation(this.location);
        p.setType(dictionaryService.getDictionaryByValue(this.type).getId());
        boolean update = organizationRepository.updateById(p);
        if (update) {
            organizationPublisher.publishUpdateOrganizationEvent(this);
        }
        // 更新子集
    }

    public void render() {
        OrganizationPO byId = organizationRepository.getById(this.id);
        this.setName(byId.getName());
        this.setStatus(dictionaryService.getDictionaryById(byId.getStatus()).getValue());
        this.setAvatar(byId.getAvatar());
        this.setImg(byId.getImg());
        this.setDescription(byId.getDescription());
        this.setAddress(byId.getAddress());
        this.setLocation(byId.getLocation());
        this.setType(dictionaryService.getDictionaryById(byId.getType()).getValue());
    }

    public void remove() {
        boolean b = organizationRepository.removeById(id);
        // 删除子集
        if (b) {
            organizationPublisher.publishRemoveOrganizationEvent(id);
        }
    }

}
