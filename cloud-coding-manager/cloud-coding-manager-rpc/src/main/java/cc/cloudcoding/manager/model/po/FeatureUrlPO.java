package cc.cloudcoding.manager.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
@AllArgsConstructor
@NoArgsConstructor
@TableName("feature_url")
public class FeatureUrlPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long featureId;

    private Long urlId;
}
