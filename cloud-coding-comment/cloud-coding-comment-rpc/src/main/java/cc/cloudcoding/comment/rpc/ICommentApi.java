package cc.cloudcoding.comment.rpc;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.comment.model.query.CommentQuery;
import cc.cloudcoding.comment.model.req.CommentReq;
import cc.cloudcoding.comment.model.res.CommentRes;

public interface ICommentApi {
    RestResponse list(CommentQuery query);

    RestResponse deleteComment(String id);

    RestResponse<CommentRes> addComment(CommentReq commentReq);

    RestResponse<Long> getCount(String id);
}