package cn.edu.hcnu.base.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.base.model.PageRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PageDTOToPageResMapping<DTO, QUERY, RES>  {


    public PageRes<RES, QUERY> sourceToTarget(PageDTO<DTO, QUERY> var1, IMapping<DTO, RES> iMapping) {
        List<RES> collect = var1.getList().stream().map(iMapping::sourceToTarget).collect(Collectors.toList());
        return new PageRes<>(collect, var1.getCount(), var1.getCommonQuery());
    }

    public PageDTO<DTO, QUERY> targetToSource(PageRes<RES, QUERY> var1, IMapping<DTO, RES> iMapping) {
        List<DTO> collect = var1.getList().stream().map(iMapping::targetToSource).collect(Collectors.toList());
        return new PageDTO<>(collect, var1.getCount(), var1.getCommonQuery());
    }


}
