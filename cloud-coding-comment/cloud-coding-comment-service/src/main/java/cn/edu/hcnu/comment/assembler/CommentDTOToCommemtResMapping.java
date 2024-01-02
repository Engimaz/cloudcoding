package cn.edu.hcnu.comment.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.comment.model.dto.CommentDTO;
import cn.edu.hcnu.comment.model.res.CommentRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentDTOToCommemtResMapping extends IMapping<CommentDTO, CommentRes> {
}
