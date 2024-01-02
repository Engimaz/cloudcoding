package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.organization.Organization;
import cn.edu.hcnu.manager.model.command.UpdateOrganizationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/4 19:13
 */

@Component
public class UpdateOrganizationCommandToOrganizationMapping implements IMapping<UpdateOrganizationCommand, Organization> {


    @Autowired
    private PositionDTOToPositionMapping positionDTOToPositionMapping;

    @Autowired
    private FeatureOrganizationToFeatureOrganizationDTOMapping featureOrganizationToFeatureOrganizationDTOMapping;

    @Autowired
    private UserPositionToUserPositionDTOMapping userPositionToUserPositionDTOMapping;

    @Autowired
    private FeatureToDTOMapping featureToDTOMapping;

    @Override
    public Organization sourceToTarget(UpdateOrganizationCommand var1) {
        Organization organization = new Organization();
        organization.setId(var1.getId());
        organization.setName(var1.getName());
        organization.setStatus(var1.getStatus());
        organization.setAvatar(var1.getAvatar());
        organization.setDescription(var1.getDescription());
        organization.setImg(var1.getImg());
        organization.setLocation(var1.getLocation());
        organization.setType(var1.getType());
        organization.setAddress(var1.getAddress());
        organization.setPositions(positionDTOToPositionMapping.sourceToTarget(var1.getPositions()));
        organization.setFeatures(featureToDTOMapping.targetToSource(var1.getFeatures()));
        organization.setUserPositions(userPositionToUserPositionDTOMapping.targetToSource(var1.getUserPositions()));
        return organization;
    }

    @Override
    public UpdateOrganizationCommand targetToSource(Organization var1) {
        UpdateOrganizationCommand addOrganizationCommand = new UpdateOrganizationCommand();
        addOrganizationCommand.setId(var1.getId());
        addOrganizationCommand.setName(var1.getName());
        addOrganizationCommand.setStatus(var1.getStatus());
        addOrganizationCommand.setAvatar(var1.getAvatar());
        addOrganizationCommand.setDescription(var1.getDescription());
        addOrganizationCommand.setImg(var1.getImg());
        addOrganizationCommand.setLocation(var1.getLocation());
        addOrganizationCommand.setType(var1.getType());
        addOrganizationCommand.setAddress(var1.getAddress());
        addOrganizationCommand.setPositions(positionDTOToPositionMapping.targetToSource(var1.getPositions()));
        addOrganizationCommand.setFeatures(featureToDTOMapping.sourceToTarget(var1.getFeatures()));
        addOrganizationCommand.setUserPositions(userPositionToUserPositionDTOMapping.sourceToTarget(var1.getUserPositions()));
        return addOrganizationCommand;
    }

    @Override
    public List<Organization> sourceToTarget(List<UpdateOrganizationCommand> var1) {
        return var1.stream().map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<UpdateOrganizationCommand> targetToSource(List<Organization> var1) {
        return var1.stream().map(this::targetToSource).collect(Collectors.toList());
    }

    @Override
    public List<Organization> sourceToTarget(Stream<UpdateOrganizationCommand> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<UpdateOrganizationCommand> targetToSource(Stream<Organization> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
