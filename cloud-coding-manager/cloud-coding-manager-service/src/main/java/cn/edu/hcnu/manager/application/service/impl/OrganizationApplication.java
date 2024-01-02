package cn.edu.hcnu.manager.application.service.impl;

import cn.edu.hcnu.auth.model.po.PositionPO;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.application.assembler.*;
import cn.edu.hcnu.manager.application.service.IOrganizationApplication;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.domain.service.feature.FeatureDomainService;
import cn.edu.hcnu.manager.domain.service.organization.Organization;
import cn.edu.hcnu.manager.domain.service.position.Position;
import cn.edu.hcnu.manager.domain.service.relation.FeatureOrganizationDomainService;
import cn.edu.hcnu.manager.domain.service.relation.UserPosition;
import cn.edu.hcnu.manager.domain.service.relation.UserPositionDomainService;
import cn.edu.hcnu.manager.infrastructure.repository.OrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.PositionRepository;
import cn.edu.hcnu.manager.model.command.AddOrganizationCommand;
import cn.edu.hcnu.manager.model.command.UpdateOrganizationCommand;
import cn.edu.hcnu.manager.model.dto.OrganizationDTO;
import cn.edu.hcnu.manager.model.dto.PositionDTO;
import cn.edu.hcnu.manager.model.po.OrganizationPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrganizationApplication implements IOrganizationApplication {


    @Autowired
    private OrganizationToOrganizationDTOMapping organizationToOrganizationDTOMapping;


    @Autowired
    private UserPositionToUserPositionDTOMapping userPositionToUserPositionDTOMapping;

    @Autowired
    private FeatureOrganizationDomainService featureOrganizationDomainService;


    @Autowired
    private FeatureDomainService featureDomainService;

    @Autowired
    private FeatureToDTOMapping featureToDTOMapping;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public OrganizationDTO queryById(Long id) {

        Organization bean = applicationContext.getBean(Organization.class);
        bean.setId(id);
        bean.render();
        // 查询本身社团
        OrganizationDTO result = organizationToOrganizationDTOMapping.sourceToTarget(bean);


        // 查询社团职位
        Optional.ofNullable(positionRepository.queryByOrganizationId(id))
                .ifPresent(positions -> {
                            List<PositionDTO> collect = positions.stream().map(item -> {
                                PositionDTO dto = new PositionDTO();
                                dto.setId(String.valueOf(item.getId()));
                                dto.setOrganizationId(String.valueOf(item.getOrganizationId()));
                                dto.setName(item.getName());
                                dto.setStatus(String.valueOf(item.getStatus()));
                                return dto;
                            }).collect(Collectors.toList());
                            // 设置社团职位
                            result.setPositions(collect);

                            // 查询职位与成员之间的关系
                            List<UserPosition> userPositions = positions.stream()
                                    .map(position -> userPositionDomainService.queryByPositionId(position.getId()))
                                    .flatMap(List::stream)
                                    .collect(Collectors.toList());


                            result.setUserPositions(userPositionToUserPositionDTOMapping.sourceToTarget(userPositions));

                        }

                );

        // 查询社团有的功能
        Optional.ofNullable(featureOrganizationDomainService.queryByOrganizationId(id))
                .ifPresent(featureOrganizations -> {
                    // 查询这个功能
                    List<Feature> features = featureOrganizations.stream().map(featureOrganization -> featureDomainService.findById(featureOrganization.getFeatureId())).collect(Collectors.toList());
                    result.setFeatures(featureToDTOMapping.sourceToTarget(features));
                });

        return result;
    }

    @Override
    public Boolean deleteById(Long id) {
        Organization bean = applicationContext.getBean(Organization.class);
        bean.setId(id);
        bean.remove();
        return true;
    }


    @Autowired
    private UserPositionDomainService userPositionDomainService;


    @Autowired
    private ApplicationContext applicationContext;
    @Qualifier("snowflake")
    @Autowired
    private IDGenerator idGenerator;

    @Autowired
    private OrganizationRepository organizationRepository;


    @Override
    public OrganizationDTO addOrganization(AddOrganizationCommand command) {
        Organization bean = applicationContext.getBean(Organization.class);
        bean.setId(Long.valueOf(idGenerator.nextID()));
        bean.setName(command.getName());
        bean.setStatus(command.getStatus());
        bean.setAvatar(command.getAvatar());
        bean.setImg(command.getImg());
        bean.setDescription(command.getDescription());
        bean.setAddress(command.getAddress());
        bean.setLocation(command.getLocation());
        bean.setType(command.getType());
        bean.setPositions(command.getPositions().stream().map(item -> {
            Position position = applicationContext.getBean(Position.class);
            position.setOrganizationId(bean.getId());
            position.setName(item.getName());
            position.setValue(item.getValue());
            position.setStatus(item.getStatus());
            position.setId(Long.valueOf(idGenerator.nextID()));
            return position;
        }).collect(Collectors.toList()));
        bean.setUserPositions(command.getUserPositions().stream().map(item -> {
            UserPosition userPosition = applicationContext.getBean(UserPosition.class);
            userPosition.setUserId(Long.valueOf(item.getUserId()));
            userPosition.setPosition(item.getPosition());
            userPosition.setId(Long.valueOf(idGenerator.nextID()));
            return userPosition;
        }).collect(Collectors.toList()));
        bean.save();
        return organizationToOrganizationDTOMapping.sourceToTarget(bean);
    }

    @Override
    public OrganizationDTO updateOrganization(UpdateOrganizationCommand command) {
        Organization bean = applicationContext.getBean(Organization.class);
        bean.setId(command.getId());
        bean.setName(command.getName());
        bean.setStatus(command.getStatus());
        bean.setAvatar(command.getAvatar());
        bean.setImg(command.getImg());
        bean.setDescription(command.getDescription());
        bean.setAddress(command.getAddress());
        bean.setLocation(command.getLocation());
        bean.setType(command.getType());
        bean.update();
        return organizationToOrganizationDTOMapping.sourceToTarget(bean);

    }

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Override
    @GetMapping("list")
    public PageDTO<OrganizationDTO, CommonQuery> list(CommonQuery commonQuery, String status) {
        Long id = null;
        if (status != null) {
            id = dictionaryService.getDictionaryByLabel(status).getId();
        }

        LambdaQueryWrapper<OrganizationPO> eq = new LambdaQueryWrapper<OrganizationPO>().eq(id != null, OrganizationPO::getStatus, id);

        Page<OrganizationPO> page = new Page<>(commonQuery.getPage(), commonQuery.getSize());

        Page<OrganizationPO> res = organizationRepository.page(page, eq);

        List<Organization> collect = res.getRecords().stream().map(po -> {
            Organization bean = applicationContext.getBean(Organization.class);
            bean.setId(po.getId());
            bean.setName(po.getName());
            bean.setStatus(dictionaryService.getDictionaryById(po.getStatus()).getValue());
            bean.setAvatar(po.getAvatar());
            bean.setImg(po.getImg());
            bean.setDescription(po.getDescription());
            bean.setAddress(po.getAddress());
            bean.setLocation(po.getLocation());
            bean.setType(dictionaryService.getDictionaryById(po.getType()).getValue());
            return bean;
        }).collect(Collectors.toList());
        return new PageDTO<>(organizationToOrganizationDTOMapping.sourceToTarget(collect), res.getTotal(), commonQuery);
    }


    @Override
    public List<OrganizationDTO> queryByUserId(Long userId) {


        // 查询用户的职位
        List<OrganizationPO> list = organizationRepository.findByUserId(userId);

        List<Organization> collect = list.stream().map(po -> {
            Organization bean = applicationContext.getBean(Organization.class);
            bean.setId(po.getId());
            bean.setName(po.getName());
            bean.setStatus(dictionaryService.getDictionaryById(po.getStatus()).getValue());
            bean.setAvatar(po.getAvatar());
            bean.setImg(po.getImg());
            bean.setDescription(po.getDescription());
            bean.setAddress(po.getAddress());
            bean.setLocation(po.getLocation());
            bean.setType(dictionaryService.getDictionaryById(po.getType()).getValue());
            return bean;
        }).collect(Collectors.toList());


        return organizationToOrganizationDTOMapping.sourceToTarget(collect);


    }
}
