package cn.edu.hcnu.course.controller.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.course.model.dto.UnitDTO;
import cn.edu.hcnu.course.model.res.UnitRes;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UnitDTOToUnitResMapping implements IMapping<UnitDTO, UnitRes> {

    @Autowired
    private NodeDTOToNodeResMapping nodeDTOToNodeResMapping;

    @Override
    public UnitRes sourceToTarget(UnitDTO var1) {
        UnitRes unitRes = new UnitRes();
        unitRes.setId(var1.getId());
        unitRes.setName(var1.getName());
        unitRes.setOrder(var1.getOrder());
        unitRes.setCourseId(var1.getCourseId());
        unitRes.setNodes(nodeDTOToNodeResMapping.sourceToTarget(var1.getNodes()));
        return unitRes;
    }

    @Override
    public UnitDTO targetToSource(UnitRes var1) {
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(var1.getId());
        unitDTO.setName(var1.getName());
        unitDTO.setOrder(var1.getOrder());
        unitDTO.setCourseId(var1.getCourseId());
        unitDTO.setNodes(nodeDTOToNodeResMapping.targetToSource(var1.getNodes()));
        return unitDTO;
    }

    @Override
    public List<UnitRes> sourceToTarget(List<UnitDTO> var1) {
        return this.sourceToTarget(var1.stream());
    }

    @Override
    public List<UnitDTO> targetToSource(List<UnitRes> var1) {
        return this.targetToSource(var1.stream());
    }

    @Override
    public List<UnitRes> sourceToTarget(Stream<UnitDTO> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<UnitDTO> targetToSource(Stream<UnitRes> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
