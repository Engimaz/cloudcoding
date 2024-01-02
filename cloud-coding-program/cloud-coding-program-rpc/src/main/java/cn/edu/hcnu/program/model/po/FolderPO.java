package cn.edu.hcnu.program.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-10-12 12:11:09
 */
@Getter
@Setter
@TableName("folder")
public class FolderPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 所属工程id
     */
    private Long programId;

    /**
     * 文件夹名称
     */
    private String name;
}
