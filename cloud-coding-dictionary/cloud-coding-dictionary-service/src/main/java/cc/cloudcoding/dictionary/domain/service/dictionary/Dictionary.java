package cc.cloudcoding.dictionary.domain.service.dictionary;

import cc.cloudcoding.dictionary.infrastructure.service.DictionaryRepository;
import cc.cloudcoding.dictionary.model.po.DictionaryPO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;



@Service
@Data
@Scope("prototype")
@RequiredArgsConstructor
public class Dictionary {


    private final DictionaryRepository dictionaryRepository;


    private Long id;
    private String label;
    private String value;
    private Long groupId;




    public void  render(){
        DictionaryPO po = dictionaryRepository.getById(this.getId());
        this.setId(po.getId());
        this.setGroupId(po.getGroupId());
        this.setLabel(po.getLabel());
        this.setValue(po.getValue());
    }


    public void renderByLabel() {
        DictionaryPO po = dictionaryRepository.getOne(new LambdaQueryWrapper<DictionaryPO>().eq(DictionaryPO::getLabel,this.getLabel()));
        this.setId(po.getId());
        this.setGroupId(po.getGroupId());
        this.setLabel(po.getLabel());
        this.setValue(po.getValue());
    }

    public void renderByValue() {
        DictionaryPO po = dictionaryRepository.getOne(new LambdaQueryWrapper<DictionaryPO>().eq(DictionaryPO::getValue,this.getValue()));
        this.setId(po.getId());
        this.setGroupId(po.getGroupId());
        this.setLabel(po.getLabel());
        this.setValue(po.getValue());
    }
}
