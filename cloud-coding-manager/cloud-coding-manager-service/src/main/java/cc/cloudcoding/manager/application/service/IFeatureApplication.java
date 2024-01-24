package cc.cloudcoding.manager.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.manager.model.command.AddFeatureCommand;
import cc.cloudcoding.manager.model.command.UpdateFeatureCommand;
import cc.cloudcoding.manager.model.dto.FeatureDTO;

public interface IFeatureApplication {
    /**
     * @param id 功能id
     * @description: 根据id查询
     * @return: cc.cloudcoding.manager.model.dto.UrlDTO
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    FeatureDTO queryById(Long id);

    /**
     * @param id 功能id
     * @description: 根据id删除
     * @return: java.lang.Boolean true 删除成功 false 删除失败
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    Boolean deleteById(Long id);

    /**
     * @param addFeatureCommand {@link AddFeatureCommand}
     * @description: 新增功能
     * @return: cc.cloudcoding.manager.model.dto.UrlDTO
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    FeatureDTO addFeature(AddFeatureCommand addFeatureCommand);

    /**
     * @param updateFeatureCommand {@link UpdateFeatureCommand}
     * @description: 更新功能
     * @return: cc.cloudcoding.manager.model.dto.UrlDTO
     * @author: Administrator
     * @time: 2023/9/2 23:25
     */
    FeatureDTO updateFeature(UpdateFeatureCommand updateFeatureCommand);

    PageDTO<FeatureDTO, CommonQuery> list(CommonQuery commonQuery);

    PageDTO<FeatureDTO, CommonQuery> all();
}
