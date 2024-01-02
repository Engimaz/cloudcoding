package cn.edu.hcnu.auth.assembler;

import cn.edu.hcnu.auth.model.res.UserRes;
import cn.edu.hcnu.auth.model.security.UserDTO;
import cn.edu.hcnu.base.assembler.IMapping;
import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.base.model.PageRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PageDTOToUserQueryResMapping implements IMapping<PageDTO<UserDTO, CommonQuery>, PageRes<UserRes, CommonQuery>> {

    @Autowired
    private UserDTOToUserResMapping userDTOToUserResMapping;
    @Override
    public PageRes<UserRes, CommonQuery> sourceToTarget(PageDTO<UserDTO, CommonQuery> var1) {

        List<UserRes> collect = var1.getList().stream().map(UserDTO -> userDTOToUserResMapping.sourceToTarget(UserDTO)).collect(Collectors.toList());
        return new PageRes<>(collect, var1.getCount(), var1.getCommonQuery());

    }

    @Override
    public PageDTO<UserDTO, CommonQuery> targetToSource(PageRes<UserRes, CommonQuery> var1) {
        Stream<UserDTO> UserDTOStream = var1.getList().stream().map(UserRes -> userDTOToUserResMapping.targetToSource(UserRes));
        return new PageDTO<>(UserDTOStream.collect(Collectors.toList()), var1.getCount(), var1.getCommonQuery());
    }

    @Override
    public List<PageRes<UserRes, CommonQuery>> sourceToTarget(List<PageDTO<UserDTO, CommonQuery>> var1) {
        return var1.stream().map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<PageDTO<UserDTO, CommonQuery>> targetToSource(List<PageRes<UserRes, CommonQuery>> var1) {
        return var1.stream().map(this::targetToSource).collect(Collectors.toList());
    }

    @Override
    public List<PageRes<UserRes, CommonQuery>> sourceToTarget(Stream<PageDTO<UserDTO, CommonQuery>> stream) {
        return stream.map(this::sourceToTarget).collect(Collectors.toList());
    }

    @Override
    public List<PageDTO<UserDTO, CommonQuery>> targetToSource(Stream<PageRes<UserRes, CommonQuery>> stream) {
        return stream.map(this::targetToSource).collect(Collectors.toList());
    }


}
