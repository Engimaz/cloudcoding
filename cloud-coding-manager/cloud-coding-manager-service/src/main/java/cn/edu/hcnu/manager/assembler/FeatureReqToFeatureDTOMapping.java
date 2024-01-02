package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.dto.FeatureDTO;
import cn.edu.hcnu.manager.model.req.FeatureReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureReqToFeatureDTOMapping extends IMapping<FeatureReq, FeatureDTO> {
}
