package cn.edu.hcnu.dictionary.infrastructure.service;

import cn.edu.hcnu.dictionary.domain.service.dictionary.Dictionary;
import cn.edu.hcnu.dictionary.domain.service.group.Group;
import cn.edu.hcnu.dictionary.model.po.DictionaryPO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author aichen
 * @since 2023-08-13 11:14:15
 */
public interface DictionaryRepository extends IService<DictionaryPO> {

    List<DictionaryPO> queryByGroupId(Long groupId);

    List<Dictionary> queryListByGroupId(Long groupId);

    Page<Group> query(Integer page, Integer size, String keyword);
}
