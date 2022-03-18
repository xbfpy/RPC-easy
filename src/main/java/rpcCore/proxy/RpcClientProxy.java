package rpcCore.proxy;

import model.client.RpcClient;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 86132
 * @create 2022/1/2 21:29
 */
public class RpcClientProxy implements InvocationHandler {
    private String host;
    private int port;
    

    public RpcClientProxy(String host,int port){
        this.host = host;
        this.port = port;
    }

    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(objects)
                .paramTypes(method.getParameterTypes())
                .build();
        RpcClient client = new RpcClient();
        return ((RpcResponse)client.sendRequest(rpcRequest,host,port)).getData();
    }
}
