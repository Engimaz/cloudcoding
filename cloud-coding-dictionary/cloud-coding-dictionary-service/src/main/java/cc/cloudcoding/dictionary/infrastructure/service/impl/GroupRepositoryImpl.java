package cc.cloudcoding.dictionary.infrastructure.service.impl;

import cc.cloudcoding.dictionary.model.po.GroupPO;
import cc.cloudcoding.dictionary.domain.service.group.Group;
import cc.cloudcoding.dictionary.infrastructure.mapper.GroupMapper;
import cc.cloudcoding.dictionary.infrastructure.service.GroupRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/24 21:50
 */
@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl extends ServiceImpl<GroupMapper, GroupPO> implements GroupRepository {


    private final ApplicationContext applicationContext;


    @Override
    public Page<Group> query(Integer page, Integer size, String keyword) {
        Page<GroupPO> pageOption = new Page<>(page, size);
        LambdaQueryWrapper<GroupPO> lqw = new LambdaQueryWrapper<GroupPO>();
        Optional.ofNullable(keyword)
                .ifPresent(k -> lqw.like(GroupPO::getName, "%" + k + "%"));

        Page<GroupPO> groupPOPage = this.getBaseMapper().selectPage(pageOption, lqw);
        List<Group> groups = getGroups(groupPOPage.getRecords());
        Page<Group> res = new Page();
        res.setRecords(groups);
        res.setTotal(groupPOPage.getTotal());
        return res;
    }


    @NotNull
    private List<Group> getGroups(List<GroupPO> list) {
        return list.stream().map(item -> {
            Group bean = applicationContext.getBean(Group.class);
            bean.setId(item.getId());
            bean.render();
            return bean;
        }).collect(Collectors.toList());
    }
}
