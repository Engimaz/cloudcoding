package cc.cloudcoding.dictionary.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/24 21:42
 */

@Getter
@Setter
@TableName("`group`")
public class GroupPO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String description;
    private String name;
}
