package cn.edu.hcnu.id.domain.service.impl;

import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;

/**
 * @description: 使用雪花算法生成6位树的id
 * @author: Administrator
 * @time: 2023/6/5 20:34
 */
@Service("snowflake")
public class SnowFlakeIDGeneratorImpl implements IDGenerator {
    //创建一个Snowflake对象，需要提供两个参数：workerId和datacenterId
    private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);

    @Override
    public String nextID() {
        //使用雪花算法生成一个long类型的ID
        long id = snowflake.nextId();
        //将long类型的ID转化为6位数字字符串，不足6位前面补0
//        return String.format("%06d", id % 1000000);
        return String.valueOf(id) ;
    }


}
