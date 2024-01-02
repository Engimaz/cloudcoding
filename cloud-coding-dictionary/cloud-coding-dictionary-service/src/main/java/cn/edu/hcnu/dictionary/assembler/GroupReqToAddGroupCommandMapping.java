package cn.edu.hcnu.dictionary.assembler;

import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.dictionary.model.command.AddGroupCommand;
import cn.edu.hcnu.dictionary.model.command.UpdateGroupCommand;
import cn.edu.hcnu.dictionary.model.req.GroupReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GroupReqToAddGroupCommandMapping implements IMapping<GroupReq, AddGroupCommand> {


    @Autowired
    private DictionaryDTOToCommandMapping dictionaryMapping;

    @Override
    public AddGroupCommand sourceToTarget(GroupReq var1) {
        return new AddGroupCommand(var1.getName(), var1.getDescription(),var1.getList().stream().map(dictionaryMapping::sourceToTarget).collect(Collectors.toList()));
    }

    @Override
    public GroupReq targetToSource(AddGroupCommand var1) {
    return new GroupReq(var1.getName(), var1.getList().stream().map(dictionaryMapping::targetToSource).collect(Collectors.toList()));
    }

    @Override
    public List<AddGroupCommand> sourceToTarget(List<GroupReq> var1) {
        return var1.stream().map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<GroupReq> targetToSource(List<AddGroupCommand> var1) {
        return var1.stream().map(this::targetToSource).collect(Collectors.toList());
    }

    @Override
    public List<AddGroupCommand> sourceToTarget(Stream<GroupReq> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<GroupReq> targetToSource(Stream<AddGroupCommand> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }
}
