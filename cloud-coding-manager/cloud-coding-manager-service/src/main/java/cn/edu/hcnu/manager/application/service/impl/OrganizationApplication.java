package cn.edu.hcnu.manager.application.service.impl;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.application.assembler.FeatureToDTOMapping;
import cn.edu.hcnu.manager.application.assembler.OrganizationToOrganizationDTOMapping;
import cn.edu.hcnu.manager.application.assembler.UserPositionToUserPositionDTOMapping;
import cn.edu.hcnu.manager.application.service.IOrganizationApplication;
import cn.edu.hcnu.manager.domain.service.feature.Feature;
import cn.edu.hcnu.manager.domain.service.organization.Organization;
import cn.edu.hcnu.manager.domain.service.position.Position;
import cn.edu.hcnu.manager.domain.service.relation.FeatureOrganization;
import cn.edu.hcnu.manager.domain.service.relation.UserPosition;
import cn.edu.hcnu.manager.infrastructure.repository.FeatureOrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.OrganizationRepository;
import cn.edu.hcnu.manager.infrastructure.repository.PositionRepository;
import cn.edu.hcnu.manager.infrastructure.repository.UserPositionRepository;
import cn.edu.hcnu.manager.model.command.AddOrganizationCommand;
import cn.edu.hcnu.manager.model.command.UpdateOrganizationCommand;
import cn.edu.hcnu.manager.model.dto.OrganizationDTO;
import cn.edu.hcnu.manager.model.dto.PositionDTO;
import cn.edu.hcnu.manager.model.po.FeatureOrganizationPO;
import cn.edu.hcnu.manager.model.po.OrganizationPO;
import cn.edu.hcnu.manager.model.po.UserPositionPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
public class OrganizationApplication implements IOrganizationApplication {


    @Autowired
    private OrganizationToOrganizationDTOMapping organizationToOrganizationDTOMapping;


    @Autowired
    private UserPositionToUserPositionDTOMapping userPositionToUserPositionDTOMapping;

    @Autowired
    private FeatureOrganizationRepository featureOrganizationRepository;


    @Autowired
    private FeatureToDTOMapping featureToDTOMapping;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserPositionRepository userPositionRepository;

    @Autowired
    private ApplicationContext applicationContext;

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
                                    .map(position -> {
                                        List<UserPositionPO> list = userPositionRepository.list(new LambdaQueryWrapper<UserPositionPO>().eq(UserPositionPO::getPositionId, position.getId()));
                                        return list.stream().map(item -> {
                                            UserPosition bean1 = applicationContext.getBean(UserPosition.class);
                                            bean1.setPositionId(item.getPositionId());
                                            bean1.setUserId(item.getUserId());
                                            bean1.setId(item.getId());
                                            return bean1;
                                        }).collect(Collectors.toList());
                                    })
                                    .flatMap(List::stream)
                                    .collect(Collectors.toList());


                            result.setUserPositions(userPositionToUserPositionDTOMapping.sourceToTarget(userPositions));

                        }

                );
        LambdaQueryWrapper<FeatureOrganizationPO> eq = new LambdaQueryWrapper<FeatureOrganizationPO>().eq(FeatureOrganizationPO::getOrganizationId, id);
        List<FeatureOrganization> collect = featureOrganizationRepository.list(eq).stream().map(m -> {
            FeatureOrganization bean1 = applicationContext.getBean(FeatureOrganization.class);
            bean1.setOrganizationId(m.getOrganizationId());
            bean1.setFeatureId(m.getFeatureId());
            bean1.setId(m.getId());
            return bean1;
        }).collect(Collectors.toList());

        // 查询社团有的功能
        Optional.of(collect)
                .ifPresent(featureOrganizations -> {
                    // 查询这个功能
                    List<Feature> features = featureOrganizations.stream().map(featureOrganization -> {
                        Feature bean1 = applicationContext.getBean(Feature.class);
                        bean1.setId(featureOrganization.getFeatureId());
                        bean1.render();
                        return bean1;
                    }).collect(Collectors.toList());
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

        Map<String, Long> positionIds = new ConcurrentHashMap<>();
        bean.setPositions(command.getPositions().stream().map(item -> {
            Long s = Long.valueOf(idGenerator.nextID());
            Position position = applicationContext.getBean(Position.class);
            position.setOrganizationId(bean.getId());
            position.setName(item.getName());
            position.setValue(item.getValue());
            position.setStatus(item.getStatus());
            position.setId(s);
            positionIds.put(item.getValue(), s);
            return position;
        }).collect(Collectors.toList()));
        bean.setUserPositions(command.getUserPositions().stream().map(item -> {
            UserPosition userPosition = applicationContext.getBean(UserPosition.class);
            userPosition.setUserId(Long.valueOf(item.getUserId()));
            userPosition.setPosition(item.getPosition());
            userPosition.setPositionId(positionIds.get(item.getPosition()));
            userPosition.setId(Long.valueOf(idGenerator.nextID()));
            return userPosition;
        }).collect(Collectors.toList()));
        bean.setFeatures(command.getFeatures().stream().map(item -> {
            Feature feature = applicationContext.getBean(Feature.class);
            feature.setId(Long.valueOf(item.getId()));
            return feature;
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
        Map<String, Long> positionIds = new ConcurrentHashMap<>();
        bean.setPositions(command.getPositions().stream().map(item -> {
            Long s = Long.valueOf(idGenerator.nextID());
            Position position = applicationContext.getBean(Position.class);
            position.setOrganizationId(bean.getId());
            position.setName(item.getName());
            position.setValue(item.getValue());
            position.setStatus(item.getStatus());
            position.setId(s);
            positionIds.put(item.getValue(), s);
            return position;
        }).collect(Collectors.toList()));
        bean.setUserPositions(command.getUserPositions().stream().map(item -> {
            UserPosition userPosition = applicationContext.getBean(UserPosition.class);
            userPosition.setUserId(Long.valueOf(item.getUserId()));
            userPosition.setPosition(item.getPosition());
            userPosition.setPositionId(positionIds.get(item.getPosition()));
            userPosition.setId(Long.valueOf(idGenerator.nextID()));
            return userPosition;
        }).collect(Collectors.toList()));
        bean.setFeatures(command.getFeatures().stream().map(item -> {
            Feature feature = applicationContext.getBean(Feature.class);
            feature.setId(Long.valueOf(item.getId()));
            return feature;
        }).collect(Collectors.toList()));
        bean.update();
        return organizationToOrganizationDTOMapping.sourceToTarget(bean);

    }

    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;

    @Override
    public PageDTO<OrganizationDTO, CommonQuery> list(CommonQuery commonQuery, String status) {
        Long stateId = null;
        if (status != null) {
            stateId = dictionaryService.getDictionaryByLabel(status).getId();
        }

        LambdaQueryWrapper<OrganizationPO> eq = new LambdaQueryWrapper<OrganizationPO>().eq(stateId != null, OrganizationPO::getStatus, stateId);

        Page<OrganizationPO> page = new Page<>(commonQuery.getPage(), commonQuery.getSize());

        Page<OrganizationPO> res = organizationRepository.page(page, eq);

        List<Organization> collect = res.getRecords().stream().map(po -> {
            Organization bean = applicationContext.getBean(Organization.class);
            bean.setId(po.getId());
            bean.render();
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
