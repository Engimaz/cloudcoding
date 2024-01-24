package cc.cloudcoding.manager.application.service;


import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.manager.model.command.AddUrlCommand;
import cc.cloudcoding.manager.model.command.UpdateUrlCommand;
import cc.cloudcoding.manager.model.dto.UrlDTO;

/**
 * url 编排过程
 *
 * @author 不才小马  邮箱:Aichen517@qq.com
 * @date 2023/08/06
 */
public interface IUrlApplication {

    /**
     * @param id 接口id
     * @description: 根据id查询
     * @return: cc.cloudcoding.manager.model.dto.UrlDTO
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    UrlDTO queryById(Long id);

    /**
     * @param id 接口id
     * @description: 根据id删除
     * @return: java.lang.Boolean true 删除成功 false 删除失败
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    Boolean deleteById(Long id);

    /**
     * @param addUrlCommand {@link AddUrlCommand}
     * @description: 新增接口
     * @return: cc.cloudcoding.manager.model.dto.UrlDTO
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    UrlDTO addUrl(AddUrlCommand addUrlCommand);

    /**
     * @param updateUrlCommand {@link UpdateUrlCommand}
     * @description: 更新接口
     * @return: cc.cloudcoding.manager.model.dto.UrlDTO
     * @author: Administrator
     * @time: 2023/9/2 23:25
     */
    UrlDTO updateUrl(UpdateUrlCommand updateUrlCommand);


    /**
     * @param commonQuery 分页查询接口
     * @description: 分页查询接口
     * @return: cc.cloudcoding.base.model.PageDTO<cc.cloudcoding.manager.model.dto.UrlDTO, cc.cloudcoding.manager.model.query.CommonQuery>
     * @author: Administrator
     * @time: 2023/9/25 12:56
     */
    PageDTO<UrlDTO, CommonQuery> listUrl(CommonQuery commonQuery);

    PageDTO<UrlDTO, CommonQuery> all();
}
