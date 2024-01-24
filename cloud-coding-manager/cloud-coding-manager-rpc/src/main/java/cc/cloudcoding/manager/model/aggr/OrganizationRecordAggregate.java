package cc.cloudcoding.manager.model.aggr;

import cc.cloudcoding.auth.model.res.UserRes;
import cc.cloudcoding.manager.model.res.OrganizationRecordRes;
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
