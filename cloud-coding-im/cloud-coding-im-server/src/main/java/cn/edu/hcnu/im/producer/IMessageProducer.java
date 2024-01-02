package cn.edu.hcnu.im.producer;

public interface IMessageProducer<T> {


    /**
     * @param roomId
     * @param message
     * @description: 往房间发送一条消息
     * @return: void
     * @author: Administrator
     * @time: 2023/10/28 11:37
     */
    void sendMessage(String key, T message);
}
