package cn.edu.hcnu.program.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/6/7 23:43
 */
@Slf4j
public class FileUtil {

    public static void saveStringToFile(String filePath, String content) {
        log.info("正在向地址 {} 写入 {}", filePath, content);
        // 创建目标文件路径对象
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            // 将字符串内容写入文件
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.info("保存文件失败: {}", e.getMessage());
        }
    }
}
