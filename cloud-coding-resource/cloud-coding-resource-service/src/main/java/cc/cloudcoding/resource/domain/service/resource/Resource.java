package cc.cloudcoding.resource.domain.service.resource;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    private String id;


    /**
     * 资源类型
     */
    private String type;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源的md5值
     */
    private String md5;

    /**
     * 资源服务器
     */
    private String host;

    /**
     * 资源所在的桶
     */
    private String bucket;

    /**
     * 资源所在的路径
     * 当 host + bucket + path 可以得出一个完整的url
     */
    private String path;

    /**
     * 资源的排序 用于资源切片时 确保切片顺序有序
     */
    private Integer order;

    /**
     * 上传者id
     */
    private Long userId;

    /**
     * 文件上传时间
     */
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
