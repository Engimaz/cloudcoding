package cc.cloudcoding.manager.domain.service.url;

import cc.cloudcoding.dictionary.model.dto.DictionaryDTO;
import cc.cloudcoding.dictionary.rpc.DictionaryService;
import cc.cloudcoding.manager.infrastructure.repository.UrlRepository;
import cc.cloudcoding.manager.model.po.UrlPO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/9/2 22:35
 */

@Service
@Data
@Scope("prototype")
@RequiredArgsConstructor
public class Url {
    /**
     * id
     */
    private Long id;
    /**
     * value 接口地址 ex: cloud-coding/diagrams/security
     */
    private String value;

    /**
     * 接口名
     */
    private String name;

    private String status;

    private String scope;
    private String description;

    private final UrlRepository urlRepository;

    @DubboReference(group = "dictionary")
    @Lazy
    private DictionaryService dictionaryService;

    public void save() {
        UrlPO po = new UrlPO();
        po.setName(this.name);
        po.setValue(this.value);
        DictionaryDTO statusPo = dictionaryService.getDictionaryByValue(this.status);
        if (statusPo != null) {
            po.setStatus(statusPo.getId());

        }
        DictionaryDTO scopePo = dictionaryService.getDictionaryByValue(this.scope);
        if (scopePo != null) {
            po.setScope(scopePo.getId());
        }

        po.setDescription(this.description);
        urlRepository.save(po);
    }


    /**
     * 更新组 只更新组的名字 如果需要添加子项 是dictionary的职责
     *
     * @return {@link Boolean}
     */
    public void update() {
        UrlPO po = new UrlPO();
        po.setName(this.name);
        po.setId(this.id);
        po.setValue(this.value);
        po.setStatus(dictionaryService.getDictionaryByValue(this.status).getId());
        po.setScope(dictionaryService.getDictionaryByValue(this.scope).getId());
        po.setDescription(this.description);
        urlRepository.updateById(po);
    }

    /**
     * 删除组
     *
     * @return {@link Boolean}
     */
    public Boolean remove() {
        return urlRepository.removeById(this.id);
    }

    /**
     * 装配组
     */
    public void render() {
        UrlPO byId = urlRepository.getById(id);

        this.setId(byId.getId());
        this.setName(byId.getName());
        this.setDescription(byId.getDescription());
        this.setValue(byId.getValue());
        this.setScope(dictionaryService.getDictionaryById(byId.getScope()).getValue());
        this.setStatus(dictionaryService.getDictionaryById(byId.getStatus()).getValue());
    }
}
