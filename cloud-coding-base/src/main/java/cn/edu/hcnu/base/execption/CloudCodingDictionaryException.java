package cn.edu.hcnu.base.execption;

import cn.edu.hcnu.base.model.ResultCode;

/**
 * @description:
 * @author: Administrator
 * @time: 2023/8/26 16:29
 */
public class CloudCodingDictionaryException extends RuntimeException {
    private String errMessage;

    public CloudCodingDictionaryException() {
        super();
    }

    public CloudCodingDictionaryException(String errMessage) {
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
