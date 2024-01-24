package cc.cloudcoding.auth.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.Position;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRolePermissionRes {

    //权限ID
    private Long permissionId;

    //权限url
    private String url;

    //权限名称
    private String permissionName;

    private List<Position> roles;
}
