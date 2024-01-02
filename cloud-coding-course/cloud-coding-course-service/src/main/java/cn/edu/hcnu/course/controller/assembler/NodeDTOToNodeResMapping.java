package cn.edu.hcnu.course.controller.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.model.dto.NodeDTO;
import cn.edu.hcnu.course.model.res.NodeRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NodeDTOToNodeResMapping extends IMapping<NodeDTO, NodeRes> {
}
