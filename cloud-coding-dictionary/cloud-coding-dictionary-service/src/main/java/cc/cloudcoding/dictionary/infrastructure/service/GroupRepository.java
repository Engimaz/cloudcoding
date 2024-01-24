package cc.cloudcoding.dictionary.infrastructure.service;

import cc.cloudcoding.dictionary.model.po.GroupPO;
import cc.cloudcoding.dictionary.domain.service.group.Group;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface GroupRepository extends IService<GroupPO> {


    Page<Group> query(Integer page, Integer size, String keyword);

}
