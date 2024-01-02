package cn.edu.hcnu.program.domain.service.code.java;


import cn.edu.hcnu.program.domain.config.CodeStoreConfig;
import cn.edu.hcnu.program.domain.config.DockerConfig;
import cn.edu.hcnu.program.domain.service.code.ProgrammingLanguage;
import cn.edu.hcnu.program.domain.service.docker.DockerDomainService;
import cn.edu.hcnu.program.domain.service.program.Program;
import cn.edu.hcnu.program.model.dto.ExecutionInfoDTO;
import cn.edu.hcnu.program.util.ExecuteCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/7 17:39
 */
@Service("javaProgramingExecutor")
@Slf4j
public class JavaPrograming implements ProgrammingLanguage {
    @Autowired
    JavaConfig javaConfig;

    @Autowired
    CodeStoreConfig codeStoreConfig;


    @Autowired
    DockerConfig dockerConfig;

    @Autowired
    private DockerDomainService dockerDomainService;

    @Override
    public ExecutionInfoDTO execute(Program program) {

        String containerName = "p-" + program.getId();
        // todo 申请端口

        CompletableFuture<ExecutionInfoDTO> runResult = CompletableFuture.supplyAsync(() -> dockerDomainService.searchContainer(containerName)).thenCompose(searchDto -> {
            // 容器不存在
            if (searchDto.getOutputData().isEmpty()) {
                //创建容器
                CompletableFuture<ExecutionInfoDTO> createResult = CompletableFuture.supplyAsync(() -> dockerDomainService.createContainer(containerName, codeStoreConfig.getLocalRootPath(), codeStoreConfig.getContainerRootPath(), javaConfig.getImageName()));
                // 创建完成直接运行容器
                return createResult.thenCompose((res1) -> CompletableFuture.supplyAsync(() -> runProgram(res1.getOutputData().substring(0, 12), codeStoreConfig.getContainerRootPath() + "/" + program.getId() + "/" + program.getName(), codeStoreConfig.getContainerRootPath() + program.getId() + "/" + program.getName() + "/" + program.getName() + ".in")));
            } else {
                // 容器存在直接启动容器
                CompletableFuture<ExecutionInfoDTO> startRes = CompletableFuture.supplyAsync(() -> dockerDomainService.startContainer(containerName));

                // 运行容器
                return startRes.thenCompose((q) -> CompletableFuture.supplyAsync(() -> runProgram(searchDto.getOutputData().substring(0, 12), codeStoreConfig.getContainerRootPath() + "/" + program.getId() + "/" + program.getName(), codeStoreConfig.getContainerRootPath() + program.getId() + "/" + program.getName() + "/" + program.getName() + ".in")));

            }
        });
        try {
            // 等待 500 秒 没有结果就直接不阻塞
            return runResult.get(500, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            log.debug("这个应该做善后工作 或者 使用 aop 操作 或者定时操作 删除本次产生的文件");
        }
    }



    private ExecutionInfoDTO runProgram(String containerID, String programPath, String inName) {

        String[] command = (dockerConfig.getEnterContainer().replace("@containerID@", containerID) + javaConfig.getRunCommand().replace("@programPath@", programPath) + " < " + inName).split(",");
        log.info("正在运行程序 执行的命令是 = {}", dockerDomainService.showCommand(command));
        return ExecuteCommand.exec(command);
    }


}
