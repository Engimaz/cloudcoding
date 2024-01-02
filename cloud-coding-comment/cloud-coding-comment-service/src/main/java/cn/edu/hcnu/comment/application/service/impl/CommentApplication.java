package cn.edu.hcnu.comment.application.service.impl;

import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.comment.application.assembler.AddCommentCommandToCommentMapping;
import cn.edu.hcnu.comment.application.assembler.CommentToCommentDTOMapping;
import cn.edu.hcnu.comment.application.service.ICommentApplication;
import cn.edu.hcnu.comment.domain.comment.CommentPublisher;
import cn.edu.hcnu.comment.domain.service.Comment;
import cn.edu.hcnu.comment.domain.service.CommentDomainService;
import cn.edu.hcnu.comment.model.command.AddCommentCommand;
import cn.edu.hcnu.comment.model.dto.CommentDTO;
import cn.edu.hcnu.comment.model.query.CommentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentApplication implements ICommentApplication {

    @Autowired
    private CommentDomainService commentDomainService;

    @Autowired
    private CommentToCommentDTOMapping commentToCommentDTOMapping;

    @Autowired
    private CommentPublisher commentPublisher;

    @Autowired
    private AddCommentCommandToCommentMapping addCommentCommandToCommentMapping;


    @Override
    public PageDTO<CommentDTO, CommentQuery> list(CommentQuery query) {
        List<Comment> list = commentDomainService.query(query.getPage(), query.getSize(), query.getParentId());
        Long count = commentDomainService.count(query.getPage(), query.getSize(), query.getParentId());
        return new PageDTO<>(commentToCommentDTOMapping.sourceToTarget(list), count, query);
    }


    @Override
    public boolean delete(String id) {
        boolean res = commentDomainService.delete(id);
        if (res) {
            // todo 删除他的评论
            commentPublisher.publishRemoveCommentEvent(id);
        }
        return res;
    }

    @Override
    public CommentDTO save(AddCommentCommand addCommentCommand) {
        Comment comment = addCommentCommandToCommentMapping.sourceToTarget(addCommentCommand);
        Comment save = commentDomainService.save(comment);
        if (save != null) {
            return commentToCommentDTOMapping.sourceToTarget(save);
        }
        return null;
    }

    @Override
    public Long getCount(String id) {
        return commentDomainService.getCount(id);
    }
}
