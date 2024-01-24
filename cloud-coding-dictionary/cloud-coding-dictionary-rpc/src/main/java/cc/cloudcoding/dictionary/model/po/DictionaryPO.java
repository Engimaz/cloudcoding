package cc.cloudcoding.dictionary.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author aichen
 * @since 2023-08-13 11:14:15
 */
@Getter
@Setter
@TableName("dictionary")
public class DictionaryPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("group_id")
    private Long groupId;

    private String label;
    private String value;

}
