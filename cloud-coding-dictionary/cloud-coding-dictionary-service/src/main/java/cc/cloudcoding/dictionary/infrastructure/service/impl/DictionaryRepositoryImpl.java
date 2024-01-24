package cc.cloudcoding.dictionary.infrastructure.service.impl;

import cc.cloudcoding.dictionary.model.po.DictionaryPO;
import cc.cloudcoding.dictionary.domain.service.dictionary.Dictionary;
import cc.cloudcoding.dictionary.domain.service.group.Group;
import cc.cloudcoding.dictionary.infrastructure.mapper.DictionaryMapper;
import cc.cloudcoding.dictionary.infrastructure.service.DictionaryRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-13 11:14:15
 */
@Repository

public class DictionaryRepositoryImpl extends ServiceImpl<DictionaryMapper, DictionaryPO> implements DictionaryRepository {


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<DictionaryPO> queryByGroupId(Long groupId) {
        List<DictionaryPO> list = this.list(new LambdaQueryWrapper<DictionaryPO>().eq(DictionaryPO::getGroupId, groupId));
        return list;
    }


    /**
     * 根据组id查询字典
     *
     * @param groupId 组id
     * @return {@link List}<{@link Dictionary}>
     */
    @Override
    public List<Dictionary> queryListByGroupId(Long groupId) {
        List<DictionaryPO> list = this.queryByGroupId(groupId);
        return list.stream().map(item -> {
            Dictionary bean = applicationContext.getBean(Dictionary.class);
            bean.setGroupId(item.getGroupId());
            bean.setId(item.getId());
            bean.setLabel(bean.getLabel());
            bean.setValue(bean.getValue());
            return bean;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<Group> query(Integer page, Integer size, String keyword) {
        return null;
    }
}
