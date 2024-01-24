package cc.cloudcoding.id.domain.service.impl;

import cc.cloudcoding.id.domain.service.IDGenerator;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;


/**
 * 对象id生成实现
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/06/30
 */
@Service("object")
public class ObjectIDGeneratorImpl implements IDGenerator {
    @Override
    public String nextID() {
        return IdUtil.objectId();
    }
}
