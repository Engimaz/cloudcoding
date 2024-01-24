package cc.cloudcoding.program.domain.service.code.cpp;


import cc.cloudcoding.program.domain.service.program.Program;
import cc.cloudcoding.program.domain.config.CodeStoreConfig;
import cc.cloudcoding.program.domain.config.DockerConfig;
import cc.cloudcoding.program.domain.service.code.ProgrammingLanguage;
import cc.cloudcoding.program.domain.service.docker.Docker;
import cc.cloudcoding.program.model.dto.ExecutionInfoDTO;
import cc.cloudcoding.program.util.ExecuteCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Service("cppProgramingExecutor")
@Slf4j
@RequiredArgsConstructor
public class CppPrograming implements ProgrammingLanguage {

    private final CPPConfig cppConfig;
    private final CodeStoreConfig codeStoreConfig;
    private final DockerConfig dockerConfig;
    private final Docker docker;

    @Override
    public ExecutionInfoDTO execute(Program program) {

        String containerName = "p-" + program.getId();
        // todo 申请端口

        CompletableFuture<ExecutionInfoDTO> runResult = CompletableFuture.supplyAsync(() -> docker.searchContainer(containerName)).thenCompose(searchDto -> {
            // 容器不存在
            if (searchDto.getOutputData().isEmpty()) {
                //创建容器
                CompletableFuture<ExecutionInfoDTO> createResult = CompletableFuture.supplyAsync(() -> docker.createContainer(containerName, codeStoreConfig.getLocalRootPath(), codeStoreConfig.getContainerRootPath(), cppConfig.getImageName()));
                // 创建完成直接运行容器
                return createResult.thenCompose((res1) -> CompletableFuture.supplyAsync(() -> runProgram(res1.getOutputData().substring(0, 12), codeStoreConfig.getContainerRootPath() + "/" + program.getId() + "/" + program.getName(), codeStoreConfig.getContainerRootPath() + program.getId() + "/" + program.getName() + "/" + program.getName() + ".in")));
            } else {
                // 容器存在直接启动容器
                CompletableFuture<ExecutionInfoDTO> startRes = CompletableFuture.supplyAsync(() -> docker.startContainer(containerName));

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

        String[] command = (dockerConfig.getEnterContainer().replace("@containerID@", containerID) + cppConfig.getRunCommand().replace("@programPath@", programPath) + " < " + inName).split(",");
        log.info("正在运行程序 执行的命令是 = {}", docker.showCommand(command));
        return ExecuteCommand.exec(command);
    }


}
