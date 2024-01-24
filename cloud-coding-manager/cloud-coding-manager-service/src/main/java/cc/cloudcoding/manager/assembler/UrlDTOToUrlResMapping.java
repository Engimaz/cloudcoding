package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.model.dto.UrlDTO;
import cc.cloudcoding.manager.model.res.UrlRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlDTOToUrlResMapping extends IMapping<UrlDTO, UrlRes> {
}
