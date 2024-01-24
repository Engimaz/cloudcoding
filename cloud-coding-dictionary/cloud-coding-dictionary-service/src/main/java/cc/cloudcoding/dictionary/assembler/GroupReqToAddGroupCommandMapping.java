package cc.cloudcoding.dictionary.assembler;

import cc.cloudcoding.base.assembler.IMapping;
import cc.cloudcoding.dictionary.model.req.GroupReq;
import cc.cloudcoding.dictionary.model.command.AddGroupCommand;
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
