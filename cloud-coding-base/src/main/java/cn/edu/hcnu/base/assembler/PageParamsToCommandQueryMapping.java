package cn.edu.hcnu.base.assembler;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageParams;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageParamsToCommandQueryMapping extends IMapping<PageParams, CommonQuery> {
}
