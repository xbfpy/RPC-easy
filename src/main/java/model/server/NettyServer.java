package model.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCore.codec.CommonDecoder;
import rpcCore.codec.CommonEncoder;
import rpcCore.register.ServiceRegistry;
import rpcCore.serializer.JsonSerializer;
import rpcCore.serializer.KryoSerializer;
import rpcCore.server.NettyServerHandler;


/**
 * @author 86132
 * @create 2022/3/18 21:41
 */
public class NettyServer implements RpcServer{
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    @Override
    public void start(int port,ServiceRegistry registry) {
        //处理客户端新连接的主"线程池"
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理连接后IO事件的从"线程池"
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            //初始化Netty服务端启动器，作为服务端入口
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    //将主从"线程池"初始化到启动器中
                    .group(bossGroup, workGroup)
                    //日志打印方式
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,256)// backlog 指定了内核为此套接口排队的最大连接个数；对于给定的监听套接口，内核要维护两个队列: 未连接队列和已连接队列 backlog 的值即为未连接队列和已连接队列的和。
                    //启用该功能时，TCP将主动探测空闲连接的有效性。可以将此功能设为TCP的心跳机制，默认心跳间隔是7200s即2小时。
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)//TCP/IP协议中针对TCP默认开启了Nagle算法。Nagle算法通过减少需要传输的数据包，来优化网络。在内核实现中，数据包的发送和接受会先做缓存，分别对应于写缓存和读缓存。 启动TCP_NODELAY，就意味着禁用了Nagle算法，允许小包的发送。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new CommonEncoder(new KryoSerializer()));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler(registry));
                        }
                    });
            //绑定端口，启动Netty，sync()代表阻塞主server线程，以执行Netty线程，不阻塞的话直接进入下面的代码，线程池就被关闭了
            ChannelFuture future = serverBootstrap.bind(port).sync();//ChannelFuture用于存储异步处理的返回结果
            //等确定通道关闭了，关闭future回到主线程
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生:",e);
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
