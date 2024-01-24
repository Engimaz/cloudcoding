package cc.cloudcoding.base.assembler;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageParams;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageParamsToCommandQueryMapping extends IMapping<PageParams, CommonQuery> {
}
