package cc.cloudcoding.forum.model.po;

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
 * @since 2023-10-15 07:28:12
 */
@Getter
@Setter
@TableName("article")
public class ArticlePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String topicId;

    /**
     * 作者
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 文章标题
     */
    private String title;

    private String content;
    private Long status;
    private String avatar;


    /**
     * 文章 标签
     */
    private String label;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
