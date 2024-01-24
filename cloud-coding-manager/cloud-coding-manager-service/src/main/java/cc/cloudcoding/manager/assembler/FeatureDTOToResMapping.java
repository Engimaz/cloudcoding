package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.FeatureDTO;
import cc.cloudcoding.manager.model.res.FeatureRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureDTOToResMapping extends IMapping<FeatureDTO, FeatureRes> {


}
