package cc.cloudcoding.manager.application.service;

import cc.cloudcoding.base.model.CommonQuery;
import cc.cloudcoding.base.model.PageDTO;
import cc.cloudcoding.manager.model.command.AddOrganizationCommand;
import cc.cloudcoding.manager.model.command.AddUrlCommand;
import cc.cloudcoding.manager.model.command.UpdateOrganizationCommand;
import cc.cloudcoding.manager.model.command.UpdateUrlCommand;
import cc.cloudcoding.manager.model.dto.OrganizationDTO;

import java.util.List;

public interface IOrganizationApplication {
    /**
     * @param id 接口id
     * @description: 根据id查询
     * @return: cn.edu.hcnu.manager.model.dto.UrlDTO
     * @author: 不才小马
     * @time: 2023/9/2 23:24
     */
    OrganizationDTO queryById(Long id);

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
    OrganizationDTO addOrganization(AddOrganizationCommand addUrlCommand);

    /**
     * @param updateUrlCommand {@link UpdateUrlCommand}
     * @description: 更新接口
     * @return: cn.edu.hcnu.manager.model.dto.UrlDTO
     * @author: Administrator
     * @time: 2023/9/2 23:25
     */
    OrganizationDTO updateOrganization(UpdateOrganizationCommand updateUrlCommand);

    PageDTO<OrganizationDTO, CommonQuery> list(CommonQuery commonQuery, String status);

    List<OrganizationDTO> queryByUserId(Long userId);
}
