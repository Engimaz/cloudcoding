package cn.edu.hcnu.comment.controller;

import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.base.model.PageRes;
import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.base.model.ResultCode;
import cn.edu.hcnu.comment.application.service.ICommentApplication;
import cn.edu.hcnu.comment.assembler.CommentDTOToCommemtResMapping;
import cn.edu.hcnu.comment.assembler.CommentReqTOAddCommentCommandMapping;
import cn.edu.hcnu.comment.model.command.AddCommentCommand;
import cn.edu.hcnu.comment.model.dto.CommentDTO;
import cn.edu.hcnu.comment.model.query.CommentQuery;
import cn.edu.hcnu.comment.model.req.CommentReq;
import cn.edu.hcnu.comment.model.res.CommentRes;
import cn.edu.hcnu.comment.rpc.ICommentApi;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@DubboService(group = "comment")
@RequestMapping("comment")
public class CommentController implements ICommentApi {

    @Autowired
    private ICommentApplication commentApplication;

    @Autowired
    private CommentDTOToCommemtResMapping commentDTOToCommemtResMapping;

    @Autowired
    private CommentReqTOAddCommentCommandMapping commentReqTOAddCommentCommandMapping;

    @Override
    @GetMapping("list")
    public RestResponse list(CommentQuery query) {
        PageDTO<CommentDTO, CommentQuery> pageDTO = commentApplication.list(query);
        if (pageDTO != null) {
            List<CommentRes> commentRes = commentDTOToCommemtResMapping.sourceToTarget(pageDTO.getList());
            return RestResponse.success(ResultCode.SUCCESS, new PageRes<>(commentRes, pageDTO.getCount(), pageDTO.getCommonQuery()));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }


    @Override
    @DeleteMapping("{id}")
    public RestResponse deleteComment(@PathVariable("id") String id) {
        boolean res = commentApplication.delete(id);
        if (res) {
            return RestResponse.success(ResultCode.SUCCESS);
        }
        return null;
    }

    @Override
    @PostMapping("new")
    public RestResponse<CommentRes> addComment(@RequestBody CommentReq commentReq) {
        AddCommentCommand addCommentCommand = commentReqTOAddCommentCommandMapping.sourceToTarget(commentReq);
        CommentDTO d = commentApplication.save(addCommentCommand);
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, commentDTOToCommemtResMapping.sourceToTarget(d));
        }
        return RestResponse.fail(ResultCode.SAVE_COMMENT_ERROR);
    }

    @Override
    @GetMapping("count/{id}")
    public RestResponse<Long> getCount(@PathVariable("id") String id) {
        Long c = commentApplication.getCount(id);
        return RestResponse.success(ResultCode.SUCCESS, c);
    }
}
