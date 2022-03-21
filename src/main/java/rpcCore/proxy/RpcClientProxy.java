package rpcCore.proxy;

import model.client.NettyClient;
import model.client.RpcClient;
import model.client.SocketRpcClient;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCommon.util.RpcMessageChecker;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author 86132
 * @create 2022/1/2 21:29
 */
public class RpcClientProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private final RpcClient rpcClient;
    

    public RpcClientProxy(RpcClient rpcClient){
        this.rpcClient = rpcClient;
    }

    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        logger.info("调用方法:{}#{}",method.getDeclaringClass(),method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(),method.getDeclaringClass().getName(),method.getName(),args,method.getParameterTypes(), false);
        RpcResponse rpcResponse = null;
        if (rpcClient instanceof NettyClient){
            try{
                rpcResponse = (RpcResponse) rpcClient.sendRequest(rpcRequest);
                //CompletableFuture<RpcResponse> completableFuture = (CompletableFuture<RpcResponse>) rpcClient.sendRequest(rpcRequest);
                //rpcResponse = completableFuture.get();

            }catch (Exception e){
                logger.error("方法调用请求发送失败",e);
                return null;
            }
        }
        if (rpcClient instanceof SocketRpcClient){
            rpcResponse = (RpcResponse) rpcClient.sendRequest(rpcRequest);
        }
        RpcMessageChecker.check(rpcRequest,rpcResponse);
        return rpcResponse.getData();
    }
}
