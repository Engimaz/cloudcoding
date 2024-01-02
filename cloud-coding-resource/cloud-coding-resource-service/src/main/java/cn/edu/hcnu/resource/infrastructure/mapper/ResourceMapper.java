package cn.edu.hcnu.resource.infrastructure.mapper;

import cn.edu.hcnu.resource.model.po.ResourcePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceMapper extends BaseMapper<ResourcePO> {
}