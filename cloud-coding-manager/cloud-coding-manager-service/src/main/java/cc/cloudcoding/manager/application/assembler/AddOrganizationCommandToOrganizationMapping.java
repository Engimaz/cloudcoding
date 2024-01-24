package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.AddOrganizationCommand;
import cc.cloudcoding.manager.domain.service.organization.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
public class AddOrganizationCommandToOrganizationMapping implements IMapping<AddOrganizationCommand, Organization> {


    @Autowired
    private PositionDTOToPositionMapping positionDTOToPositionMapping;
    @Autowired
    private FeatureToDTOMapping featureToDTOMapping;

    @Autowired
    private UserPositionToUserPositionDTOMapping userPositionToUserPositionDTOMapping;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public Organization sourceToTarget(AddOrganizationCommand var1) {
        Organization organization = applicationContext.getBean(Organization.class);
        organization.setName(var1.getName());
        organization.setStatus(var1.getStatus());
        organization.setDescription(var1.getDescription());
        organization.setAddress(var1.getAddress());
        organization.setLocation(var1.getLocation());
        organization.setImg(var1.getImg());
        organization.setAvatar(var1.getAvatar());
        organization.setType(var1.getType());
        if (null != var1.getPositions()) {
            organization.setPositions(positionDTOToPositionMapping.sourceToTarget(var1.getPositions()));
        }
        if (null != var1.getFeatures()) {

            organization.setFeatures(featureToDTOMapping.targetToSource(var1.getFeatures()));
        }
        if (var1.getUserPositions() != null) {
            organization.setUserPositions(userPositionToUserPositionDTOMapping.targetToSource(var1.getUserPositions()));
        }
        return organization;
    }

    @Override
    public AddOrganizationCommand targetToSource(Organization var1) {
        AddOrganizationCommand addOrganizationCommand = new AddOrganizationCommand();
        addOrganizationCommand.setName(var1.getName());
        addOrganizationCommand.setStatus(var1.getStatus());
        if (null != var1.getPositions()) {
            addOrganizationCommand.setPositions(positionDTOToPositionMapping.targetToSource(var1.getPositions()));
        }
        if (null != var1.getFeatures()) {
            addOrganizationCommand.setFeatures(featureToDTOMapping.sourceToTarget(var1.getFeatures()));
        }
        if (var1.getUserPositions() != null) {
            addOrganizationCommand.setUserPositions(userPositionToUserPositionDTOMapping.sourceToTarget(var1.getUserPositions()));
        }
        return addOrganizationCommand;
    }

    @Override
    public List<Organization> sourceToTarget(List<AddOrganizationCommand> var1) {
        return var1.stream().map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<AddOrganizationCommand> targetToSource(List<Organization> var1) {
        return var1.stream().map(this::targetToSource).collect(Collectors.toList());
    }

    @Override
    public List<Organization> sourceToTarget(Stream<AddOrganizationCommand> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<AddOrganizationCommand> targetToSource(Stream<Organization> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
