package cn.edu.hcnu.base.execption;

import cn.edu.hcnu.base.model.ResultCode;

/**
 * 云上编程自定义异常
 *
 * @author Administrator
 * @date 2023/06/04
 */
public class CloudCodingException extends RuntimeException {

    private String errMessage;

    public CloudCodingException() {
        super();
    }

    public CloudCodingException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    /**
     * 静态方法抛出一个异常
     *
     * @param commonError 常见错误
     */
    public static void cast(ResultCode commonError) {
        throw new CloudCodingException(commonError.getInfo());
    }

    public static void cast(String errMessage) {
        throw new CloudCodingException(errMessage);
    }
}
