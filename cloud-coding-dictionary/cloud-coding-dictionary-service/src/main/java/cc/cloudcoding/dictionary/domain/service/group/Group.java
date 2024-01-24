package cc.cloudcoding.dictionary.domain.service.group;

import cc.cloudcoding.dictionary.infrastructure.service.DictionaryRepository;
import cc.cloudcoding.dictionary.infrastructure.service.GroupRepository;
import cc.cloudcoding.dictionary.model.po.DictionaryPO;
import cc.cloudcoding.dictionary.model.po.GroupPO;
import cc.cloudcoding.dictionary.domain.group.GroupPublisher;
import cc.cloudcoding.dictionary.domain.service.dictionary.Dictionary;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 接口组的领域服务
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/24
 */
@Service
@Data
@Scope("prototype")
@RequiredArgsConstructor
public class Group {


    private Long id;
    private String name;
    private String description;
    private List<Dictionary> list;


    private final GroupRepository groupRepository;


    private final GroupPublisher groupPublisher;

    private  final ApplicationContext applicationContext;
    private final Dictionary dictionDomainService;
    private final DictionaryRepository dictionaryRepository;



    public List<Dictionary> getDictionaryById(Long id) {
        List<DictionaryPO> list = dictionaryRepository.queryByGroupId(id);

        return list.stream().map(item -> {
            Dictionary bean = applicationContext.getBean(Dictionary.class);
            bean.setId(item.getId());
            bean.setLabel(item.getLabel());
            bean.setGroupId(item.getGroupId());
            bean.setValue(item.getValue());
            return bean;
        }).collect(Collectors.toList());

    }



    /**
     * 更新组 只更新组的名字 如果需要添加子项 是dictionary的职责
     *
     * @return {@link Boolean}
     */
    public void update() {
        GroupPO groupPO = new GroupPO();
        groupPO.setDescription(this.getDescription());
        groupPO.setId(this.getId());
        groupPO.setName(this.getName());
        // 新的数据
        boolean b = groupRepository.updateById(groupPO);
        if (!b) {
            return ;
        }
        // 发布组更新事件
        groupPublisher.publishUpdateGroupEvent(this);
    }

    /**
     * 删除组
     *
     * @return {@link Boolean}
     */
    public Boolean remove() {

        boolean b = groupRepository.removeById(this.getId());
        if (!b) {
            return null;
        }
        // 通知其他服务 删除组下的所有字典
        groupPublisher.publishRemoveGroupEvent(id);
        return true;
    }

    /**
     * 装配组
     */
    public void render() {
        GroupPO groupPO = groupRepository.getById(id);
        this.setId(groupPO.getId());
        this.setName(groupPO.getName());
        this.setDescription(groupPO.getDescription());
        this.setList(this.getDictionaryById(groupPO.getId()));
    }

    /**
     * 装配组通过名字
     */
    public void renderByName() {
        GroupPO groupPO = groupRepository.getOne(new LambdaQueryWrapper<GroupPO>().eq(GroupPO::getName, this.getName()));
        if(groupPO!=null){
            this.setId(groupPO.getId());
            this.setName(groupPO.getName());
            this.setDescription(groupPO.getDescription());
            this.setList(this.getDictionaryById(groupPO.getId()));
        }

    }

    public void save() {
        GroupPO groupPO = new GroupPO();
        groupPO.setDescription(this.getDescription());
        groupPO.setId(this.getId());
        groupPO.setName(this.getName());
        // 新的数据
        boolean b = groupRepository.save(groupPO);
        if (!b) {
            return ;
        }
        // 发布组更新事件
        groupPublisher.publishCreateGroupEvent(this);
    }
}
