package cn.edu.hcnu.dictionary.infrastructure.service;

import cn.edu.hcnu.dictionary.domain.service.group.Group;
import cn.edu.hcnu.dictionary.model.po.GroupPO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GroupRepository extends IService<GroupPO> {


    Page<Group> query(Integer page, Integer size, String keyword);

    List<Group> listAll();
}
