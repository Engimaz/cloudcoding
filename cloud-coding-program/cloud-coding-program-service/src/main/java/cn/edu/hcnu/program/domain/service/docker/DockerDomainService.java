package cn.edu.hcnu.program.domain.service.docker;

import cn.edu.hcnu.program.domain.config.DockerConfig;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;
import cn.edu.hcnu.program.model.pojo.ExecutionInfo;
import cn.edu.hcnu.program.util.ExecuteCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/10/15 13:53
 */

@Component
@Slf4j
public class DockerDomainService {

    @Autowired
    private DockerConfig dockerConfig;

    /**
     * 创建容器
     *
     * @param programID         程序id
     * @param localPath         本地路径
     * @param containerRootPath 容器根路径
     * @param imageName         镜像名字
     * @return {@link ExecutionInfo}
     */
    public ExecutionInfoDTO createContainer(String programID, String localPath, String containerRootPath, String imageName) {
        // 3.1 构造命令
        String[] command = dockerConfig.getCreateContainer().replace("@programID@", programID).replace("@localPath@", localPath).replace("@containerPath@", containerRootPath).replace("@imageName@", imageName).split(",");
        log.info("创建容器 执行的命令是 command = {}", showCommand(command));

        return ExecuteCommand.exec(command);
    }

    public ExecutionInfoDTO searchContainer(String containerName) {
        String[] command = dockerConfig.getSearchContainer().replace("@containerName@", containerName).split(",");
        return ExecuteCommand.exec(command);
    }


    public ExecutionInfoDTO startContainer(String containerName) {
        String[] command = dockerConfig.getStartContainer().replace("@containerName@", containerName).split(",");
        showCommand(command);
        return ExecuteCommand.exec(command);
    }

    public  ExecutionInfoDTO stopContainer(String containerName){
        String[] command = dockerConfig.getStopContainer().replace("@containerName@", containerName).split(",");
        showCommand(command);
        return ExecuteCommand.exec(command);
    }

    public String showCommand(String[] commands) {
        StringBuilder res = new StringBuilder();
        for (String command : commands) {
            res.append(command).append(" ");
        }
        return res.toString();
    }


}
