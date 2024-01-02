package cn.edu.hcnu.manager.application.service;


import cn.edu.hcnu.base.model.CommonQuery;
import cn.edu.hcnu.base.model.PageDTO;
import cn.edu.hcnu.manager.model.command.AddUrlCommand;
import cn.edu.hcnu.manager.model.command.UpdateUrlCommand;
import cn.edu.hcnu.manager.model.dto.UrlDTO;

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
     * @return: cn.edu.hcnu.manager.model.dto.UrlDTO
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
     * @return: cn.edu.hcnu.manager.model.dto.UrlDTO
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    UrlDTO addUrl(AddUrlCommand addUrlCommand);

    /**
     * @param updateUrlCommand {@link UpdateUrlCommand}
     * @description: 更新接口
     * @return: cn.edu.hcnu.manager.model.dto.UrlDTO
     * @author: Administrator
     * @time: 2023/9/2 23:25
     */
    UrlDTO updateUrl(UpdateUrlCommand updateUrlCommand);


    /**
     * @param commonQuery 分页查询接口
     * @description: 分页查询接口
     * @return: cn.edu.hcnu.base.model.PageDTO<cn.edu.hcnu.manager.model.dto.UrlDTO, cn.edu.hcnu.manager.model.query.CommonQuery>
     * @author: Administrator
     * @time: 2023/9/25 12:56
     */
    PageDTO<UrlDTO, CommonQuery> listUrl(CommonQuery commonQuery);
}
