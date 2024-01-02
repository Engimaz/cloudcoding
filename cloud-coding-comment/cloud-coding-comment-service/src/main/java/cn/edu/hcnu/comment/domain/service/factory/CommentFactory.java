package cn.edu.hcnu.comment.domain.service.factory;


import cn.edu.hcnu.comment.domain.service.Comment;
import cn.edu.hcnu.comment.model.po.CommentPO;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CommentFactory {

    @Autowired
    @Qualifier(value = "snowflake")
    private IDGenerator idGenerator;


    public Comment createComment(CommentPO po) {
        Comment comment = new Comment();
        comment.setId(String.valueOf(po.getId()));
        comment.setContent(po.getContent());
        comment.setUserId(String.valueOf(po.getUserId()));
        comment.setParentId(String.valueOf(po.getParentId()));
        comment.setReplyId(String.valueOf(po.getReplyId()));
        comment.setCreateTime(po.getCreateTime());
        comment.setUpdateTime(po.getUpdateTime());
        return comment;
    }

    public CommentPO createCommentPO(Comment comment) {
        CommentPO po = new CommentPO();
        if(comment.getId()==null){
            po.setId(Long.valueOf(idGenerator.nextID()));
        }else{
            po.setId(Long.valueOf(comment.getId()));
        }
        po.setContent(comment.getContent());
        po.setUserId(Long.valueOf(comment.getUserId()));
        po.setParentId(Long.valueOf(comment.getParentId()));
        po.setReplyId(Long.valueOf(comment.getReplyId()));
        po.setCreateTime(comment.getCreateTime());
        po.setUpdateTime(comment.getUpdateTime());
        return po;
    }
}
