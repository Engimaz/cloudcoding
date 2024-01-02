package cn.edu.hcnu.manager.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.rpc.DictionaryService;
import cn.edu.hcnu.manager.model.dto.FeatureDTO;
import cn.edu.hcnu.manager.model.dto.FeatureUrlDTO;
import cn.edu.hcnu.manager.model.res.FeatureRes;
import cn.edu.hcnu.manager.model.res.FeatureUrlRes;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class FeatureDTOToResMapping implements IMapping<FeatureDTO, FeatureRes> {


    @DubboReference(group = "dictionary")
    protected DictionaryService dictionaryService;


    @Override
    public FeatureRes sourceToTarget(FeatureDTO var1) {
        FeatureRes featureVO = new FeatureRes();
        featureVO.setId(String.valueOf(var1.getId()));
        featureVO.setName(var1.getName());
        featureVO.setStatus(dictionaryService.getDictionaryByLabel(String.valueOf(var1.getStatus())).getLabel());
        featureVO.setRelations(var1.getRelations().stream().map(f -> new FeatureUrlRes(String.valueOf(f.getId()), String.valueOf(f.getFeatureId()), String.valueOf(f.getUrlId()))).collect(Collectors.toList()));
        return featureVO;
    }

    @Override
    public FeatureDTO targetToSource(FeatureRes var1) {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setId(var1.getId());
        featureDTO.setName(var1.getName());
        featureDTO.setStatus(dictionaryService.getDictionaryByLabel(var1.getStatus()).getLabel());
        featureDTO.setRelations(var1.getRelations().stream().map(f -> new FeatureUrlDTO(Long.valueOf(f.getId()), Long.valueOf(f.getFeatureId()), Long.valueOf(f.getUrlId()))).collect(Collectors.toList()));
        return featureDTO;
    }

    @Override
    public List<FeatureRes> sourceToTarget(List<FeatureDTO> var1) {
        return var1.stream().map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<FeatureDTO> targetToSource(List<FeatureRes> var1) {
        return var1.stream().map(this::targetToSource).collect(Collectors.toList());
    }

    @Override
    public List<FeatureRes> sourceToTarget(Stream<FeatureDTO> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());

    }

    @Override
    public List<FeatureDTO> targetToSource(Stream<FeatureRes> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
