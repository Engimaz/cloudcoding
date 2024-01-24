package cc.cloudcoding.id.domain.service.impl;

import cc.cloudcoding.id.domain.service.IDGenerator;
import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.stereotype.Service;


/**
 * ulid 生成id 实现
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/06/30
 */
@Service("ulid")
public class ULIDGeneratorImpl implements IDGenerator {
    @Override
    public String nextID() {
        Ulid ulid = UlidCreator.getUlid();
        return ulid.toString();
    }
}
