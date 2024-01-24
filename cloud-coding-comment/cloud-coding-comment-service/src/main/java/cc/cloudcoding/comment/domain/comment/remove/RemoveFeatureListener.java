package cc.cloudcoding.comment.domain.comment.remove;

import cc.cloudcoding.comment.domain.service.CommentDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/12 13:46
 */
@Service

public class RemoveFeatureListener {

    @Autowired
    private CommentDomainService commentDomainService;

    @EventListener
    public void handleCustomEvent(RemoveCommentEvent event) {

        // 删除回复他的人
        commentDomainService.deleteByReplyId(event.getRemoveId());
        // 假如一个层主则整个层都删除
        commentDomainService.deleteByParentId(event.getRemoveId());
    }
}
