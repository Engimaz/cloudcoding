package cc.cloudcoding.im.session;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionManager {

    private static SessionManager instance;
    private ConcurrentMap<String, Channel> userChannel;

    private SessionManager() {
        userChannel = new ConcurrentHashMap<>();
    }



    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // 用户上线 添加用户进管理器中
    public void addChanel(Channel channel){
        userChannel.put(channel.id().asLongText(),channel);
    }
    // 用户下线移除用户
    public void removeChanel(Channel channel){
        userChannel.remove(channel.id().asLongText());
    }
    public  Channel getChannel(String id){
        return userChannel.get(id);
    }
}
