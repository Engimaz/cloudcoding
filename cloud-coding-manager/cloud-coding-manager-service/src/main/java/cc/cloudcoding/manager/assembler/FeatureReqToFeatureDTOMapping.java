package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.FeatureDTO;
import cc.cloudcoding.manager.model.req.FeatureReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureReqToFeatureDTOMapping extends IMapping<FeatureReq, FeatureDTO> {
}
