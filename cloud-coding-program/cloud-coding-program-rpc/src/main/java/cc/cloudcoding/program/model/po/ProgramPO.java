package cc.cloudcoding.program.model.po;

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
 * @since 2023-10-12 12:11:09
 */
@Getter
@Setter
@TableName("program")
public class ProgramPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工程名
     */
    private String name;

    /**
     * 工程描述
     */
    private String description;

    private String language;


    private String sdk;
    private String avatar;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
