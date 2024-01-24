package cc.cloudcoding.im.runner;

import cc.cloudcoding.im.handler.WebSocketHandler;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * 初始化Netty服务
 *
 * @author AICHEN
 */
@Component
@Slf4j
@PropertySource(value = "classpath:application.yml")
public class NettyBootstrapRunner implements ApplicationRunner {


    @Value("${netty.websocket.port}")
    private int port;

    @Value("${netty.websocket.name}")
    private String name;


    @Value("${netty.websocket.path}")
    private String path;


    @Value("${spring.cloud.nacos.discovery.namespace}")
    private String namespace;

//    @Value("${netty.websocket.ip}")
//    private String ip;

    @Value("${netty.websocket.max-frame-size}")
    private long maxFrameSize;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    /**
     * 注册到 nacos 服务中
     *
     * @param nettyName netty服务名称
     * @param nettyPort netty服务端口
     */
    private void registerNamingService(String nettyName, String nettyPort) {
        try {
            // 设置命名空间
            nacosDiscoveryProperties.getNacosProperties().put("namespace", namespace);

            NamingService namingService = NamingFactory.createNamingService(nacosDiscoveryProperties.getNacosProperties());
            InetAddress address = InetAddress.getLocalHost();
            // address.getHostAddress()
            log.info("注册服务到nacos中，服务名称：{}，服务地址：{}", nettyName, address.getHostAddress());

            namingService.registerInstance(nettyName, address.getHostAddress(), Integer.parseInt(nettyPort));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //InetAddress.getLocalHost().getHostAddress()
        log.info("正在启动 WebSocket {}:{}", InetAddress.getLocalHost().getHostAddress(), port);
        EventLoopGroup bossGroup = new NioEventLoopGroup();// boss 工作线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();// worker 工作线程
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);

        //注册到Nacos里
        registerNamingService(name, String.valueOf(port));


        try {

            serverBootstrap.channel(NioServerSocketChannel.class);// 设置channel
            serverBootstrap.localAddress(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), this.port));// 指定端口绑定
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    // 网络解码器
                    pipeline.addLast(new HttpServerCodec());
                    // 防止信息过大 内存泄露
                    pipeline.addLast(new ChunkedWriteHandler());
                    // http 聚合 将会产生 FullHttpRequest 和 FullHttpResponse
                    pipeline.addLast(new HttpObjectAggregator(65536));
                    // 添加 websocket 协议
                    pipeline.addLast(new WebSocketServerProtocolHandler(path, "protocol", true, maxFrameSize));
                    // 处理前后端消息交互
                    pipeline.addLast(applicationContext.getBean(WebSocketHandler.class));


                }
            });
            Channel channel = serverBootstrap.bind().sync().channel();
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
