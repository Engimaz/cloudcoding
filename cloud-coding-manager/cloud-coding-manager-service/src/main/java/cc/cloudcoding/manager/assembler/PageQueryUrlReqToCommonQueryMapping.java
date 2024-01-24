package cc.cloudcoding.manager.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.manager.model.req.PageQueryUrlReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageQueryUrlReqToCommonQueryMapping extends IMapping<PageQueryUrlReq, CommonQuery> {
}
