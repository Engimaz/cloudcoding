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
@TableName("program_user")
public class ProgramUserPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long programId;

    /**
     * 该用户在此工程中的角色 负责人 协助者
     */
    private String role;
}
