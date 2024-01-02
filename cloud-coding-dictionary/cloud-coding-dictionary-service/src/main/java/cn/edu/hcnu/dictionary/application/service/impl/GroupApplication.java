package cn.edu.hcnu.dictionary.application.service.impl;

import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.dictionary.application.assembler.DictionaryMapping;
import cn.edu.hcnu.dictionary.application.assembler.GroupMapping;
import cn.edu.hcnu.dictionary.application.service.IGroupApplication;
import cn.edu.hcnu.dictionary.domain.service.dictionary.Dictionary;
import cn.edu.hcnu.dictionary.domain.service.group.Group;
import cn.edu.hcnu.dictionary.infrastructure.service.DictionaryRepository;
import cn.edu.hcnu.dictionary.infrastructure.service.GroupRepository;
import cn.edu.hcnu.dictionary.model.command.AddGroupCommand;
import cn.edu.hcnu.dictionary.model.command.UpdateGroupCommand;
import cn.edu.hcnu.dictionary.model.dto.GroupDTO;
import cn.edu.hcnu.dictionary.model.query.GroupPageQuery;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 12:43
 */
@Service
@RequiredArgsConstructor
public class GroupApplication implements IGroupApplication {


    private final GroupMapping groupMapping;
    private final DictionaryMapping dictionaryMapping;

    private final ApplicationContext applicationContext;
    private final GroupRepository groupRepository;
    @Qualifier("snowflake")
    @Autowired
    private  IDGenerator idGenerator;
    private final DictionaryRepository dictionaryRepository;

    @Override
    public GroupDTO queryById(Long id) {
        Group bean = applicationContext.getBean(Group.class);
        bean.setId(id);
        bean.render();
        return groupMapping.sourceToTarget(bean);
    }

    @Override
    public GroupDTO queryByName(String name) {
        Group bean = applicationContext.getBean(Group.class);
        bean.setName(name);
        bean.renderByName();
        return groupMapping.sourceToTarget(bean);
    }

    @Override
    public PageDTO<GroupDTO, CommonQuery> query( CommonQuery query) {

        Page<Group> page = groupRepository.query(query.getPage(), query.getSize(), query.getKeyword());

        return new PageDTO<>(groupMapping.sourceToTarget(page.getRecords()), page.getTotal(),query);

    }

    @Override
    public GroupDTO addGroup(@Valid AddGroupCommand addGroupCommand) {

        Group bean = applicationContext.getBean(Group.class);
        bean.setId(Long.parseLong(idGenerator.nextID()));
        bean.setDescription(addGroupCommand.getDescription());
        bean.setName(addGroupCommand.getName());
        bean.setList(addGroupCommand.getList().stream().map(item->{
            Dictionary dictionary = applicationContext.getBean(Dictionary.class);
            dictionary.setGroupId(bean.getId());
            dictionary.setId(Long.parseLong(idGenerator.nextID()));
            dictionary.setLabel(item.getLabel());
            dictionary.setValue(item.getValue());
            return  dictionary;
        }).collect(Collectors.toList()));

        bean.save();

        // 这个是domain转换成dto
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(bean.getId());
        groupDTO.setName(bean.getName());
        groupDTO.setDescription(bean.getDescription());
        groupDTO.setList(dictionaryMapping.sourceToTarget(dictionaryRepository.queryListByGroupId(bean.getId())));

        return groupDTO;
    }

    @Override
    public GroupDTO updateGroup(UpdateGroupCommand updateGroupCommand) {
        // 数据转换

        Group bean = applicationContext.getBean(Group.class);
        bean.setId(updateGroupCommand.getId());
        bean.setDescription(updateGroupCommand.getDescription());
        bean.setName(updateGroupCommand.getName());
        bean.setList(updateGroupCommand.getList().stream().map(item->{
            Dictionary dictionary = applicationContext.getBean(Dictionary.class);
            dictionary.setGroupId(bean.getId());
            if(item.getId()!=null){
                dictionary.setId(item.getId());
            }else{
                dictionary.setId(Long.parseLong(idGenerator.nextID()));
            }
            dictionary.setLabel(item.getLabel());
            dictionary.setValue(item.getValue());
            return  dictionary;
        }).collect(Collectors.toList()));



        bean.update();
        // 数据转换
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(bean.getId());
        groupDTO.setName(bean.getName());
        groupDTO.setDescription(bean.getDescription());
        groupDTO.setList(dictionaryMapping.sourceToTarget(bean.getList()));
        return groupDTO;
    }

    @Override
    public Boolean deleteGroup(Long id) {
        Group bean = applicationContext.getBean(Group.class);
        bean.setId(id);
        return bean.remove();
    }


}
