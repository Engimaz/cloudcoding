package cc.cloudcoding.comment.domain.service;

import cc.cloudcoding.comment.domain.service.factory.CommentFactory;
import cc.cloudcoding.comment.infrastructure.mapper.CommentMapper;
import cc.cloudcoding.comment.infrastructure.repository.CommentRepository;
import cc.cloudcoding.comment.model.po.CommentPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentDomainService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentFactory commentFactory;

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> query(Integer page, Integer size, String parentId) {
        return this.pageQuery(page, size, parentId).getRecords().stream().map((f) -> commentFactory.createComment(f)).collect(Collectors.toList());
    }


    public Long count(Integer page, Integer size, String parentId) {
        return this.pageQuery(page, size, parentId).getTotal();
    }

    private Page<CommentPO> pageQuery(Integer page, Integer size, String parentId) {
        Page<CommentPO> p1 = new Page<>(page, size);
        LambdaQueryWrapper<CommentPO> wrapper = new LambdaQueryWrapper<CommentPO>().eq(CommentPO::getParentId, parentId);
        return commentRepository.getBaseMapper().selectPage(p1, wrapper);
    }

    public boolean delete(String id) {
        return commentRepository.removeById(id);
    }

    public boolean deleteByReplyId(String removeId) {
        return commentRepository.remove(new LambdaQueryWrapper<CommentPO>().eq(CommentPO::getReplyId, removeId));
    }

    public boolean deleteByParentId(String removeId) {
        return commentRepository.remove(new LambdaQueryWrapper<CommentPO>().eq(CommentPO::getParentId, removeId));
    }

    public Comment save(Comment comment) {
        CommentPO po = commentFactory.createCommentPO(comment);
        boolean save = commentRepository.save(po);
        if (save) {
            return commentFactory.createComment(po);
        }
        return null;
    }

    public Long getCount(String id) {
        return commentMapper.getCount(id);
    }
}
