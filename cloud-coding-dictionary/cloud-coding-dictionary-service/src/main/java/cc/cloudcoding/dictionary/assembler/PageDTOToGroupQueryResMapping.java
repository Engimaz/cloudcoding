package cc.cloudcoding.dictionary.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.base.model.PageRes;
import cc.cloudcoding.dictionary.model.dto.GroupDTO;
import cc.cloudcoding.dictionary.model.res.GroupRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PageDTOToGroupQueryResMapping implements IMapping<PageDTO<GroupDTO, CommonQuery>, PageRes<GroupRes, CommonQuery>> {

    @Autowired
    private GroupDTOToResMapping groupDTOToResMapping;

    @Override
    public PageRes<GroupRes, CommonQuery> sourceToTarget(PageDTO<GroupDTO, CommonQuery> var1) {

        List<GroupRes> collect = var1.getList().stream().map(groupDTO -> groupDTOToResMapping.sourceToTarget(groupDTO)).collect(Collectors.toList());
        return new PageRes<>(collect, var1.getCount(), var1.getCommonQuery());

    }

    @Override
    public PageDTO<GroupDTO, CommonQuery> targetToSource(PageRes<GroupRes, CommonQuery> var1) {
        Stream<GroupDTO> groupDTOStream = var1.getList().stream().map(groupRes -> groupDTOToResMapping.targetToSource(groupRes));
        return new PageDTO<>(groupDTOStream.collect(Collectors.toList()), var1.getCount(), var1.getCommonQuery());
    }

    @Override
    public List<PageRes<GroupRes, CommonQuery>> sourceToTarget(List<PageDTO<GroupDTO, CommonQuery>> var1) {
        return var1.stream().map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<PageDTO<GroupDTO, CommonQuery>> targetToSource(List<PageRes<GroupRes, CommonQuery>> var1) {
        return var1.stream().map(this::targetToSource).collect(Collectors.toList());
    }

    @Override
    public List<PageRes<GroupRes, CommonQuery>> sourceToTarget(Stream<PageDTO<GroupDTO, CommonQuery>> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<PageDTO<GroupDTO, CommonQuery>> targetToSource(Stream<PageRes<GroupRes, CommonQuery>> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
