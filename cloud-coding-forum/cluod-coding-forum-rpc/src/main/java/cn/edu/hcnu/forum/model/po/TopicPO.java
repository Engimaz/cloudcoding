package cn.edu.hcnu.forum.model.po;

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
@TableName("topic")
public class TopicPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建者
     */
    private Long userId;

    /**
     * 专栏名称
     */
    private String name;

    /**
     * 专栏描述
     */
    private String description;

    /**
     * 专栏标签
     */
    private String label;

    /*
     * 文章类型
     */
    private String type;

    /**
     * 专栏创建时间
     */
    @TableField(fill = FieldFill.INSERT)

    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)

    private LocalDateTime updateTime;
}
