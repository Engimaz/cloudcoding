package cc.cloudcoding.manager.application.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.manager.domain.service.url.Url;
import cc.cloudcoding.manager.model.dto.UrlDTO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:56
 */
@Mapper(componentModel = "spring")
public interface UrlToDTOMapping extends IMapping<Url, UrlDTO> {
}
