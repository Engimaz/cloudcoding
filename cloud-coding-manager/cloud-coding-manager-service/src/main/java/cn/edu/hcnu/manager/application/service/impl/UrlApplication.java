package cn.edu.hcnu.manager.application.service.impl;

import cn.edu.hcnu.manager.model.po.UrlPO;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.id.domain.service.IDGenerator;
import cn.edu.hcnu.manager.application.assembler.UrlToDTOMapping;
import cn.edu.hcnu.manager.application.service.IUrlApplication;
import cn.edu.hcnu.manager.domain.service.url.Url;
import cn.edu.hcnu.manager.infrastructure.repository.UrlRepository;
import cn.edu.hcnu.manager.model.command.AddUrlCommand;
import cn.edu.hcnu.manager.model.command.UpdateUrlCommand;
import cn.edu.hcnu.manager.model.dto.UrlDTO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;



@Component
@RequiredArgsConstructor
public class UrlApplication implements IUrlApplication {



    private final UrlToDTOMapping urlToDTOMapping;



    private final ApplicationContext applicationContext;
    @Qualifier("snowflake")
    @Autowired
    private IDGenerator idGenerator;

    private final UrlRepository urlRepository;


    @DubboReference(group = "dictionary")
    private DictionaryService dictionaryService;
    @Override
    public UrlDTO queryById(Long id) {
        Url bean = applicationContext.getBean(Url.class);
        bean.setId(id);
        bean.render();
        return urlToDTOMapping.sourceToTarget(bean);
    }

    @Override
    public Boolean deleteById(Long id) {
        Url bean = applicationContext.getBean(Url.class);
        bean.setId(id);
        return bean.remove();
    }

    @Override
    public UrlDTO addUrl(AddUrlCommand addUrlCommand) {
        Url bean = applicationContext.getBean(Url.class);
        bean.setId(Long.valueOf(idGenerator.nextID()));
        bean.setName(addUrlCommand.getName());
        bean.setScope(addUrlCommand.getScope());
        bean.setStatus(addUrlCommand.getStatus());
        bean.setDescription(addUrlCommand.getDescription());
        bean.setValue(addUrlCommand.getValue());
        bean.save();
        return urlToDTOMapping.sourceToTarget(bean);
    }

    @Override
    public UrlDTO updateUrl(UpdateUrlCommand updateUrlCommand) {
        Url bean = applicationContext.getBean(Url.class);
        bean.setId(Long.valueOf(updateUrlCommand.getId()));
        bean.setName(updateUrlCommand.getName());
        bean.setScope(updateUrlCommand.getScope());
        bean.setStatus(updateUrlCommand.getStatus());
        bean.setDescription(updateUrlCommand.getDescription());
        bean.setValue(updateUrlCommand.getValue());
        bean.save();
        return urlToDTOMapping.sourceToTarget(bean);
    }

    @Override
    public PageDTO<UrlDTO, CommonQuery> listUrl(CommonQuery commonQuery) {
        LambdaQueryWrapper<UrlPO> like = new LambdaQueryWrapper<UrlPO>().like(StrUtil.isNotBlank(commonQuery.getKeyword()), UrlPO::getName, commonQuery.getKeyword());

        Page<UrlPO> page = new Page<>(commonQuery.getPage(),commonQuery.getSize());
        IPage<UrlPO> res = urlRepository.page(page, like);

        List<Url> collect = res.getRecords().stream().map(po -> {
            Url url = applicationContext.getBean(Url.class);
            url.setId(po.getId());
            url.setName(po.getName());
            url.setValue(po.getValue());
            url.setScope(dictionaryService.getDictionaryById(po.getScope()).getValue());
            url.setStatus(dictionaryService.getDictionaryById(po.getStatus()).getValue());
            url.setDescription(po.getDescription());
            return url;
        }).collect(Collectors.toList());

        return new PageDTO<>(urlToDTOMapping.sourceToTarget(collect)  , res.getTotal(), commonQuery);
    }
}
