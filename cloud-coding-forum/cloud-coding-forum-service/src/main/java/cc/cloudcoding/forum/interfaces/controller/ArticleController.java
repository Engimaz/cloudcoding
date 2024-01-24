package cc.cloudcoding.forum.interfaces.controller;

import cc.cloudcoding.base.assembler.PageDTOToPageResMapping;
import cc.cloudcoding.base.assembler.PageParamsToCommandQueryMapping;
import cc.cloudcoding.base.model.*;
import cn.edu.hcnu.forum.application.service.IArticleApplication;
import cn.edu.hcnu.forum.interfaces.assembler.ArticleDTOToArticleResMapping;
import cn.edu.hcnu.forum.interfaces.assembler.ArticleReqToAddArticleCommandMapping;
import cn.edu.hcnu.forum.interfaces.assembler.ArticleReqToUpdateArticleCommandMapping;
import cn.edu.hcnu.forum.model.command.AddArticleCommand;
import cn.edu.hcnu.forum.model.command.UpdateArticleCommand;
import cn.edu.hcnu.forum.model.dto.ArticleDTO;
import cn.edu.hcnu.forum.model.req.ArticleReq;
import cn.edu.hcnu.forum.rpc.ArticleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 19:55
 */
@RequestMapping("/article")
@RestController
public class ArticleController implements ArticleApi {

    @Autowired
    private ArticleReqToAddArticleCommandMapping articleReqToAddArticleCommandMapping;

    @Autowired
    private ArticleReqToUpdateArticleCommandMapping articleReqToUpdateArticleCommandMapping;

    @Autowired
    private ArticleDTOToArticleResMapping articleDTOToArticleResMapping;

    @Autowired
    private PageParamsToCommandQueryMapping pageParamsToCommandQueryMapping;

    @Autowired
    private IArticleApplication articleApplication;

    @Autowired
    private PageDTOToPageResMapping pageDTOToPageResMapping;

    @Override
    @PostMapping("new")
    public RestResponse create(@RequestBody ArticleReq req) {
        AddArticleCommand addArticleCommand = articleReqToAddArticleCommandMapping.sourceToTarget(req);
        ArticleDTO d = articleApplication.save(addArticleCommand);
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, articleDTOToArticleResMapping.sourceToTarget(d));
        }
        return RestResponse.fail(ResultCode.ARTICLE_CREATE_FAIL);
    }

    @Override
    @PutMapping("update")
    public RestResponse update(@RequestBody ArticleReq req) {
        UpdateArticleCommand addArticleCommand = articleReqToUpdateArticleCommandMapping.sourceToTarget(req);
        ArticleDTO d = articleApplication.update(addArticleCommand);
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, articleDTOToArticleResMapping.sourceToTarget(d));
        }
        return RestResponse.fail(ResultCode.ARTICLE_UPDATE_FAIL);
    }

    @GetMapping("list")
    public RestResponse listByStatus(PageParams req, @RequestParam("status") String status) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<ArticleDTO, CommonQuery> list = articleApplication.listByStatus(commonQuery, status);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, pageDTOToPageResMapping.sourceToTarget(list, articleDTOToArticleResMapping));
    }

    @GetMapping("list/{userId}")
    public RestResponse list(PageParams req, @PathVariable("userId") String userId) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<ArticleDTO, CommonQuery> list = articleApplication.list(commonQuery, userId);
        if (null == list) {
            return RestResponse.fail(ResultCode.QUERY_ERROR);
        }
        return RestResponse.success(ResultCode.SUCCESS, pageDTOToPageResMapping.sourceToTarget(list, articleDTOToArticleResMapping));
    }

    /**
     * 查询一篇文章
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public RestResponse get(@PathVariable("id") Long id) {
        ArticleDTO d = articleApplication.get(id);
        if (d != null) {
            return RestResponse.success(ResultCode.SUCCESS, articleDTOToArticleResMapping.sourceToTarget(d));
        }
        return RestResponse.fail(ResultCode.ARTICLE_QUERY_FAIL);
    }

    /**
     * 删除一篇文章
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public RestResponse delete(@PathVariable("id") Long id) {
        boolean d = articleApplication.delete(id);
        if (d) {
            return RestResponse.success(ResultCode.SUCCESS, d);
        }
        return RestResponse.fail(ResultCode.ARTICLE_DELETE_FAIL);
    }


}
