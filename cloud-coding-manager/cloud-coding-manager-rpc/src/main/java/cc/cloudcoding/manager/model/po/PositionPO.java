package cc.cloudcoding.manager.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Getter
@Setter
@TableName("position")
public class PositionPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long organizationId;

    /**
     * 职位名称
     */
    private String name;


    private Long status;

    protected String value;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
