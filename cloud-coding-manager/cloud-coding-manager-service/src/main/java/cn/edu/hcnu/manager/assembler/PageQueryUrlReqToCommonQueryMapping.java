package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.manager.model.req.PageQueryUrlReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageQueryUrlReqToCommonQueryMapping extends IMapping<PageQueryUrlReq, CommonQuery> {
}
