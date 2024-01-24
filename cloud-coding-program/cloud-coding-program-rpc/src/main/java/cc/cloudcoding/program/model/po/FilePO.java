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
 * @since 2023-10-12 12:11:08
 */
@Getter
@Setter
@TableName("file")
public class FilePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long folderId;

    private String content;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
