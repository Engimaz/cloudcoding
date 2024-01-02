package cn.edu.hcnu.forum.rpc;

import cn.edu.hcnu.base.model.RestResponse;
import cn.edu.hcnu.forum.model.req.ArticleReq;

public interface ArticleApi {

    RestResponse create(ArticleReq req);

    RestResponse update(ArticleReq req);
}
