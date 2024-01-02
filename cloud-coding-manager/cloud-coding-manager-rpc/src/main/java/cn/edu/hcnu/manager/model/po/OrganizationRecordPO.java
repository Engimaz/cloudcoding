package cn.edu.hcnu.manager.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("organization_record")
public class OrganizationRecordPO {

    @TableId
    private Long id;
    private Long userId;
    private Long organizationId;
    private String content;
}
