package cc.cloudcoding.course.controller.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.course.model.command.NodeCommand;
import cc.cloudcoding.course.model.req.NodeReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NodeReqToNodeCommandMapping extends IMapping<NodeReq, NodeCommand> {
}
