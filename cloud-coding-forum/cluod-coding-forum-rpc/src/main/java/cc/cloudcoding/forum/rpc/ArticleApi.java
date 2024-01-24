package cc.cloudcoding.forum.rpc;

import cc.cloudcoding.base.model.RestResponse;
import cc.cloudcoding.forum.model.req.ArticleReq;

public interface ArticleApi {

    RestResponse create(ArticleReq req);

    RestResponse update(ArticleReq req);
}
