package cn.edu.hcnu.resource.domain.service.factory.product;

import lombok.Data;

@Data
public abstract class OssResource {
    private String id;

    // 资源名称
    private String name;

    // 资源路径
    private String path;

    // 资源大小
    private Long size;

    // 资源的md5
    private String md5;

    // 资源类型 video/mp4
    private String type;



}
