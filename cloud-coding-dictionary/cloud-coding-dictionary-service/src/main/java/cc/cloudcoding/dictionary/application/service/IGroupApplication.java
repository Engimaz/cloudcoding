package cc.cloudcoding.dictionary.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.dictionary.model.dto.GroupDTO;
import cc.cloudcoding.dictionary.model.command.AddGroupCommand;
import cc.cloudcoding.dictionary.model.command.UpdateGroupCommand;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 12:43
 */
public interface IGroupApplication {

    GroupDTO queryById(Long id);

    PageDTO<GroupDTO, CommonQuery> query(CommonQuery query);

    GroupDTO addGroup(AddGroupCommand command);

    GroupDTO updateGroup(UpdateGroupCommand command);

    Boolean deleteGroup(Long id);

    GroupDTO queryByName(String name);
}
