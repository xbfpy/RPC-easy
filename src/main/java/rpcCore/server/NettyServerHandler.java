package rpcCore.server;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;
import rpcCore.handler.RequestHandler;
import rpcCore.register.DefaultServiceRegistry;
import rpcCore.register.ServiceRegistry;

import java.rmi.registry.Registry;

/**
 * @author 86132
 * @create 2022/3/19 20:57
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;
    static{
        requestHandler = new RequestHandler();
    }
    public NettyServerHandler(ServiceRegistry registry){
        serviceRegistry = registry;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try{
            logger.info("服务器接收到请求:{}",msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handler(msg,service);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result,msg.getRequestId()));
            future.addListener(ChannelFutureListener.CLOSE);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        logger.error("处理过程有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
