package cn.edu.hcnu.program.infrastructure.mapper;

import cn.edu.hcnu.program.model.po.FilePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author aichen
 * @since 2023-10-12 12:11:08
 */
@Mapper
public interface FileMapper extends BaseMapper<FilePO> {

}
