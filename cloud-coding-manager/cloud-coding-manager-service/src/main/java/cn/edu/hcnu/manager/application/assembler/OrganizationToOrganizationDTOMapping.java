package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.organization.Organization;
import cn.edu.hcnu.manager.model.dto.OrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/4 19:27
 */

@Component
public class OrganizationToOrganizationDTOMapping implements IMapping<Organization, OrganizationDTO> {

    @Autowired
    private PositionDTOToPositionMapping positionDTOToPositionMapping;


    @Autowired
    private FeatureToDTOMapping featureToDTOMapping;

    @Autowired
    private UserPositionToUserPositionDTOMapping userPositionToUserPositionDTOMapping;

    @Override
    public OrganizationDTO sourceToTarget(Organization var1) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(var1.getId());
        organizationDTO.setName(var1.getName());
        organizationDTO.setStatus(var1.getStatus());
        organizationDTO.setAvatar(var1.getAvatar());
        organizationDTO.setImg(var1.getImg());
        organizationDTO.setDescription(var1.getDescription());
        organizationDTO.setAddress(var1.getAddress());
        organizationDTO.setLocation(var1.getLocation());
        organizationDTO.setType(var1.getType());
        organizationDTO.setPositions(positionDTOToPositionMapping.targetToSource(var1.getPositions()));
        organizationDTO.setFeatures(featureToDTOMapping.sourceToTarget(var1.getFeatures()));
        organizationDTO.setUserPositions(userPositionToUserPositionDTOMapping.sourceToTarget(var1.getUserPositions()));
        return organizationDTO;
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Organization targetToSource(OrganizationDTO var1) {
        Organization organization = applicationContext.getBean(Organization.class);
        organization.setId(var1.getId());
        organization.setName(var1.getName());
        organization.setStatus(var1.getStatus());
        organization.setAddress(var1.getAddress());
        organization.setImg(var1.getImg());
        organization.setDescription(var1.getDescription());
        organization.setAddress(var1.getAddress());
        organization.setLocation(var1.getLocation());
        organization.setType(var1.getType());
        organization.setUserPositions(userPositionToUserPositionDTOMapping.targetToSource(var1.getUserPositions()));
        organization.setPositions(positionDTOToPositionMapping.sourceToTarget(var1.getPositions()));
        organization.setFeatures(featureToDTOMapping.targetToSource(var1.getFeatures()));
        return organization;
    }

    @Override
    public List<OrganizationDTO> sourceToTarget(List<Organization> var1) {
        return var1.stream().map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<Organization> targetToSource(List<OrganizationDTO> var1) {
        return var1.stream().map(this::targetToSource).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationDTO> sourceToTarget(Stream<Organization> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<Organization> targetToSource(Stream<OrganizationDTO> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
