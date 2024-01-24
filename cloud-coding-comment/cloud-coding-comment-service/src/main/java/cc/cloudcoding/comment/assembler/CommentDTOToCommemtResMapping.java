package cc.cloudcoding.comment.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.comment.model.dto.CommentDTO;
import cc.cloudcoding.comment.model.res.CommentRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentDTOToCommemtResMapping extends IMapping<CommentDTO, CommentRes> {
}
