package cc.cloudcoding.auth.utils;

import cc.cloudcoding.base.model.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author AICHEN
 *         结果封装类
 */
public class ResponseUtils {

    public static void result(HttpServletResponse response, RestResponse msg, String mediaType) throws IOException {
        response.setContentType(mediaType);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream out = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        out.write(objectMapper.writeValueAsString(msg).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
