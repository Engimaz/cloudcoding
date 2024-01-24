package cc.cloudcoding.program.util;

import cc.cloudcoding.program.model.dto.ExecutionInfoDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @description: 命令执行器
 * @author: Administrator
 * @time: 2023/6/7 22:46
 */
@Slf4j
public class ExecuteCommand {
    public static ExecutionInfoDTO exec(String command, String inputData) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return exec(process, inputData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static ExecutionInfoDTO exec(String[] command, String inputData) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return exec(process, inputData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ExecutionInfoDTO exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return exec(process, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ExecutionInfoDTO exec(String[] command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return exec(process, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ExecutionInfoDTO exec(Process process, String inputData) {

        // 1. 设置程序输入数据 bug 在本地有效 但是进入了 docker 后就不是一个正确的程序了 因此 当进入docker 需要读取本地文件作为程序的输入
        if (inputData != null && !"".equals(inputData.trim())) {
            // 向程序输入数据
            OutputStream is = process.getOutputStream();
            PrintWriter pw = new PrintWriter(is);
            pw.println(inputData);
            pw.close();
        }

        ExecutionInfoDTO executionInfo = new ExecutionInfoDTO();

        // 读取标准输出流和标准错误流
        try (BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            // 读取子进程的输出流和错误流
            String line;

            // 临时保存输出数据与错误数据
            StringBuffer out = new StringBuffer();
            StringBuffer err = new StringBuffer();

            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
                out.append(line);
            }
            while ((line = stderr.readLine()) != null) {
                System.err.println(line);
                err.append(line);
            }
            // 等待子进程运行完成
            int exitCode = process.waitFor();

            // 设置返回信息
            executionInfo.setOutputData(out.toString());
            executionInfo.setErrorData(err.toString());
            process.destroy();

            log.debug("子进程已退出，退出码为：{}  本次执行的数据产生的结果是： {}", exitCode, executionInfo);

        } catch (IOException e) {
            // 处理IO异常
            e.printStackTrace();
        } catch (InterruptedException e) {
            // 子进程被中断
            e.printStackTrace();
        }
        return executionInfo;
    }
}
