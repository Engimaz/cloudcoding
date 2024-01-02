package cn.edu.hcnu.manager.application.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.manager.domain.service.url.Url;
import cn.edu.hcnu.manager.model.dto.UrlDTO;
import org.mapstruct.Mapper;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 14:56
 */
@Mapper(componentModel = "spring")
public interface UrlToDTOMapping extends IMapping<Url, UrlDTO> {
}
