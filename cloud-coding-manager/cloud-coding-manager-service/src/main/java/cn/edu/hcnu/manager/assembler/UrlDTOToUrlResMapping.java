package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.model.dto.UrlDTO;
import cn.edu.hcnu.manager.model.res.UrlRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlDTOToUrlResMapping extends IMapping<UrlDTO, UrlRes> {
}
