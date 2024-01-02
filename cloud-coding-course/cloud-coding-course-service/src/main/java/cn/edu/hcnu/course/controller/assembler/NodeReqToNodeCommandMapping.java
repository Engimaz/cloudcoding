package cn.edu.hcnu.course.controller.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.model.command.NodeCommand;
import cn.edu.hcnu.course.model.command.UnitCommand;
import cn.edu.hcnu.course.model.req.NodeReq;
import cn.edu.hcnu.course.model.req.UnitReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NodeReqToNodeCommandMapping extends IMapping<NodeReq, NodeCommand> {
}
