package cc.cloudcoding.comment.application.service.impl;

import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.comment.application.assembler.AddCommentCommandToCommentMapping;
import cc.cloudcoding.comment.application.assembler.CommentToCommentDTOMapping;
import cc.cloudcoding.comment.application.service.ICommentApplication;
import cc.cloudcoding.comment.domain.comment.CommentPublisher;
import cc.cloudcoding.comment.domain.service.Comment;
import cc.cloudcoding.comment.domain.service.CommentDomainService;
import cc.cloudcoding.comment.model.command.AddCommentCommand;
import cc.cloudcoding.comment.model.dto.CommentDTO;
import cc.cloudcoding.comment.model.query.CommentQuery;
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
