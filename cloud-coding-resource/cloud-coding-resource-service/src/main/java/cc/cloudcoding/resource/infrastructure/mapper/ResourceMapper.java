package cc.cloudcoding.resource.infrastructure.mapper;

import cc.cloudcoding.resource.model.po.ResourcePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceMapper extends BaseMapper<ResourcePO> {
}