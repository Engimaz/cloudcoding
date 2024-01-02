package cn.edu.hcnu.comment.rpc;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.comment.model.query.CommentQuery;
import cn.edu.hcnu.comment.model.req.CommentReq;
import cn.edu.hcnu.comment.model.res.CommentRes;

public interface ICommentApi {
    RestResponse list(CommentQuery query);

    RestResponse deleteComment(String id);

    RestResponse<CommentRes> addComment(CommentReq commentReq);

    RestResponse<Long> getCount(String id);
}