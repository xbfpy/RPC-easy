package model.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCore.codec.CommonDecoder;
import rpcCore.codec.CommonEncoder;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;
import rpcCore.serializer.JsonSerializer;
import rpcCore.server.NettyClientHandler;

/**
 * @author 86132
 * @create 2022/3/18 21:41
 */
public class NettyClient implements RpcClient{
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host,int port){
        this.host = host;
        this.port = port;
    }
    static {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelPipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try{
            ChannelFuture future = bootstrap.connect(host,port).sync();
            logger.info("客户端连接到服务器 {}:{}",host,port);
            Channel channel = future.channel();
            if (channel!=null){
                //发送消息是异步的，发送后会直接返回
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()){
                        logger.info(String.format("客户端发送消息:%s",rpcRequest.toString()));
                    }else{
                        logger.error("发送消息时有错误发生:",future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();//等待数据返回
                return rpcResponse;
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生:",e);
        }
        return null;
    }
}
