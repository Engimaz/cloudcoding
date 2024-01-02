package cn.edu.hcnu.manager.model.aggr;

import cn.edu.hcnu.auth.model.res.UserRes;
import cn.edu.hcnu.auth.model.security.UserDTO;
import cn.edu.hcnu.manager.model.dto.OrganizationRecordDTO;
import cn.edu.hcnu.manager.model.res.OrganizationRecordRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/26 0:54
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRecordAggregate {
    private List<OrganizationRecordRes> records;
    private List<UserRes> users;
}
