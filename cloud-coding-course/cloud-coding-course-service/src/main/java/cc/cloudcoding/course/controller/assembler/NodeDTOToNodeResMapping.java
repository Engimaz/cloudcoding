package cc.cloudcoding.course.controller.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.course.model.dto.NodeDTO;
import cc.cloudcoding.course.model.res.NodeRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NodeDTOToNodeResMapping extends IMapping<NodeDTO, NodeRes> {
}
