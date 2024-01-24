package cc.cloudcoding.manager.infrastructure.repository.impl;

import cc.cloudcoding.manager.model.po.UrlPO;
import cc.cloudcoding.manager.infrastructure.mapper.UrlMapper;
import cc.cloudcoding.manager.infrastructure.repository.UrlRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Repository
public class UrlRepositoryImpl extends ServiceImpl<UrlMapper, UrlPO> implements UrlRepository {

}
