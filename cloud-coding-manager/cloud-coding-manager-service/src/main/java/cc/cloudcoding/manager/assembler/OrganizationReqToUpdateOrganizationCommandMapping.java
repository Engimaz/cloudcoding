package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.command.UpdateOrganizationCommand;
import cc.cloudcoding.manager.model.req.OrganizationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OrganizationReqToUpdateOrganizationCommandMapping implements IMapping<OrganizationReq, UpdateOrganizationCommand> {

    @Autowired
    private FeatureReqToFeatureDTOMapping featureReqToFeatureDTOMapping;

    @Autowired
    private PositionReqToPositionDTOMapping positionReqToPositionDTOMapping;

    @Autowired
    private UserPositionReqToUserPositionDTOMapping userPositionReqToUserPositionDTOMapping;

    @Override
    public UpdateOrganizationCommand sourceToTarget(OrganizationReq var1) {

        UpdateOrganizationCommand updateOrganizationCommand = new UpdateOrganizationCommand();
        updateOrganizationCommand.setId(Long.valueOf(var1.getId()));
        updateOrganizationCommand.setName(var1.getName());
        updateOrganizationCommand.setStatus(var1.getStatus());
        updateOrganizationCommand.setAvatar(var1.getAvatar());
        updateOrganizationCommand.setImg(var1.getImg());
        updateOrganizationCommand.setDescription(var1.getDescription());
        updateOrganizationCommand.setAddress(var1.getAddress());
        updateOrganizationCommand.setLocation(var1.getLocation());
        updateOrganizationCommand.setType(var1.getType());
        updateOrganizationCommand.setFeatures(featureReqToFeatureDTOMapping.sourceToTarget(var1.getFeatures()));
        updateOrganizationCommand.setPositions(positionReqToPositionDTOMapping.sourceToTarget(var1.getPositions()));
        updateOrganizationCommand.setUserPositions(userPositionReqToUserPositionDTOMapping.sourceToTarget(var1.getUserPositions()));
        return updateOrganizationCommand;

    }

    @Override
    public OrganizationReq targetToSource(UpdateOrganizationCommand var1) {

        OrganizationReq u = new OrganizationReq();
        u.setId(String.valueOf(var1.getId()));
        u.setName(var1.getName());
        u.setStatus(var1.getStatus());
        u.setAvatar(var1.getAvatar());
        u.setImg(var1.getImg());
        u.setDescription(var1.getDescription());
        u.setAddress(var1.getAddress());
        u.setLocation(var1.getLocation());
        u.setType(var1.getType());
        u.setFeatures(featureReqToFeatureDTOMapping.targetToSource(var1.getFeatures()));
        u.setPositions(positionReqToPositionDTOMapping.targetToSource(var1.getPositions()));
        u.setUserPositions(userPositionReqToUserPositionDTOMapping.targetToSource(var1.getUserPositions()));


        return u;
    }

    @Override
    public List<UpdateOrganizationCommand> sourceToTarget(List<OrganizationReq> var1) {
        return this.sourceToTarget(var1.stream());
    }

    @Override
    public List<OrganizationReq> targetToSource(List<UpdateOrganizationCommand> var1) {
        return this.targetToSource(var1.stream());
    }

    @Override
    public List<UpdateOrganizationCommand> sourceToTarget(Stream<OrganizationReq> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationReq> targetToSource(Stream<UpdateOrganizationCommand> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());

    }
}
