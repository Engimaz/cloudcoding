package cn.edu.hcnu.dictionary.application.service;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.dictionary.model.command.AddGroupCommand;
import cn.edu.hcnu.dictionary.model.command.UpdateGroupCommand;
import cn.edu.hcnu.dictionary.model.dto.GroupDTO;
import cn.edu.hcnu.dictionary.model.query.GroupPageQuery;

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
