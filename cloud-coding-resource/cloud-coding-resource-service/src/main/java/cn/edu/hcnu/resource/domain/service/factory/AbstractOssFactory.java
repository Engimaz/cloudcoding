package cn.edu.hcnu.resource.domain.service.factory;

import cn.edu.hcnu.resource.domain.service.factory.product.OssResource;
import cn.edu.hcnu.resource.domain.service.factory.product.UploadResource;

/**
 * 抽象oss工厂
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/31
 */
public interface AbstractOssFactory {

    // 上传资源
    OssResource uploadResource(UploadResource uploadResource);


    // 获得资源访问点 同时也是下载点
    String getEnterPoint(Long id);

    // 合并资源
    OssResource mergeResource(String s, String md5);

    // 删除资源
    boolean removeResource(String id);
}
