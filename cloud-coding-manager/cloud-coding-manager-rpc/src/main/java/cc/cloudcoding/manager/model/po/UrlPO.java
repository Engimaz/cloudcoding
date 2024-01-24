package cc.cloudcoding.manager.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author aichen
 * @since 2023-08-05 02:13:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("url")
public class UrlPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口名
     */
    private String name;

    /**
     * 接口
     */
    private String value;

    private Long status;

    private Long scope;
    private String description;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
