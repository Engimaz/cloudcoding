package cc.cloudcoding.forum.interfaces.controller;

import cc.cloudcoding.base.assembler.PageDTOToPageResMapping;
import cc.cloudcoding.base.assembler.PageParamsToCommandQueryMapping;
import cc.cloudcoding.base.model.*;
import cn.edu.hcnu.forum.application.service.ITopicApplication;
import cn.edu.hcnu.forum.interfaces.assembler.TopicDTOToTopicResMapping;
import cn.edu.hcnu.forum.interfaces.assembler.TopicReqToAddTopicCommandMapping;
import cn.edu.hcnu.forum.interfaces.assembler.TopicReqToUpdateTopicCommandMapping;
import cn.edu.hcnu.forum.model.command.AddTopicCommand;
import cn.edu.hcnu.forum.model.command.UpdateTopicCommand;
import cn.edu.hcnu.forum.model.dto.TopicDTO;
import cn.edu.hcnu.forum.model.req.TopicReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/29 9:26
 */

@RequestMapping("/topic")
@RestController
public class TopicController {

    @Autowired
    private TopicReqToAddTopicCommandMapping topicReqToAddTopicCommandMapping;
    @Autowired
    private TopicReqToUpdateTopicCommandMapping topicReqToUpdateTopicCommandMapping;
    @Autowired
    private TopicDTOToTopicResMapping topicDTOToTopicResMapping;
    @Autowired
    private ITopicApplication topicApplication;

    @Autowired
    private PageParamsToCommandQueryMapping pageParamsToCommandQueryMapping;

    @Autowired
    private PageDTOToPageResMapping pageDTOToPageResMapping;

    @PostMapping("new")
    public RestResponse create(@RequestBody TopicReq req) {
        AddTopicCommand addTopicCommand = topicReqToAddTopicCommandMapping.sourceToTarget(req);
        TopicDTO dto = topicApplication.add(addTopicCommand);
        if (dto != null) {
            return RestResponse.success(ResultCode.SUCCESS, topicDTOToTopicResMapping.sourceToTarget(dto));
        }
        return RestResponse.fail(ResultCode.TOPIC_CREATE_FAIL);
    }

    @PutMapping("update")
    public RestResponse update(@RequestBody TopicReq req) {
        UpdateTopicCommand updateTopicCommand = topicReqToUpdateTopicCommandMapping.sourceToTarget(req);
        TopicDTO dto = topicApplication.update(updateTopicCommand);
        if (dto != null) {
            return RestResponse.success(ResultCode.SUCCESS, topicDTOToTopicResMapping.sourceToTarget(dto));
        }
        return RestResponse.fail(ResultCode.TOPIC_CREATE_FAIL);
    }

    @GetMapping("list")
    public RestResponse list(PageParams req) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<TopicDTO, CommonQuery> list = topicApplication.list(commonQuery);
        if (list != null) {
            return RestResponse.success(ResultCode.SUCCESS, pageDTOToPageResMapping.sourceToTarget(list, topicDTOToTopicResMapping));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }

    @GetMapping("list/{userId}")
    public RestResponse listByUserId(PageParams req, @PathVariable("userId") String userId) {
        CommonQuery commonQuery = pageParamsToCommandQueryMapping.sourceToTarget(req);
        PageDTO<TopicDTO, CommonQuery> list = topicApplication.list(commonQuery, userId);
        if (list != null) {
            return RestResponse.success(ResultCode.SUCCESS, pageDTOToPageResMapping.sourceToTarget(list, topicDTOToTopicResMapping));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }

    @GetMapping("{id}")
    public RestResponse get(@PathVariable("id") String id) {
        TopicDTO dto = topicApplication.get(id);
        if (dto != null) {
            return RestResponse.success(ResultCode.SUCCESS, topicDTOToTopicResMapping.sourceToTarget(dto));
        }
        return RestResponse.fail(ResultCode.QUERY_ERROR);
    }

    @DeleteMapping("{id}/{delete}")
    public RestResponse delete(@PathVariable("id") String id, @PathVariable("delete") boolean delete) {
        boolean b = topicApplication.delete(id, delete);
        if (b) {
            return RestResponse.success(ResultCode.SUCCESS);

        }
        return RestResponse.fail(ResultCode.DELETE_ERROR);


    }
}
