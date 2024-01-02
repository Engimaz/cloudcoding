package cn.edu.hcnu.resource.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("resource")
public class ResourcePO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private String id;


    /**
     * 资源类型
     */
    private String type;

    /**
     * 资源名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 资源的md5值
     */
    private String md5;

    /**
     * 资源服务器
     */
    @TableField("`host`")
    private String host;

    /**
     * 资源所在的桶
     */
    @TableField("bucket")
    private String bucket;

    /**
     * 资源所在的路径
     * 当 host + bucket + path 可以得出一个完整的url
     */
    @TableField("path")
    private String path;

    /**
     * 资源的排序 用于资源切片时 确保切片顺序有序
     */
    @TableField("`order`")
    private Integer order;

    /**
     * 上传者id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 文件上传时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)

    private LocalDateTime createTime;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件状态 1:正常 2:不显示
     */
    private Integer state;

    private String chunkMd5;


}
