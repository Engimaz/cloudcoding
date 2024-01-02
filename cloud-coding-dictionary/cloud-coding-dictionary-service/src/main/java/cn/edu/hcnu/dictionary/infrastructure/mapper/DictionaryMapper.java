package cn.edu.hcnu.dictionary.infrastructure.mapper;

import cn.edu.hcnu.dictionary.model.po.DictionaryPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author aichen
 * @since 2023-08-13 11:14:15
 */

@Mapper
public interface DictionaryMapper extends BaseMapper<DictionaryPO> {

}
