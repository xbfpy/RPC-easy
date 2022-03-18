package rpcCore.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCommon.enumeration.ResponseCode;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 86132
 * @create 2022/3/17 22:03
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handler(RpcRequest rpcRequest,Object service){
        Object result = null;
        try{
            result = invokeTargetMethod(rpcRequest,service);
            logger.info("服务：{} 成功调用方法：{}",rpcRequest.getInterfaceName(),rpcRequest.getMethodName());
        } catch (IllegalAccessException |InvocationTargetException e) {
            logger.error("调用时有错误发生：",e);
        }
        return result;
    }
    private Object invokeTargetMethod(RpcRequest rpcRequest,Object service) throws InvocationTargetException, IllegalAccessException {
        Method method;
        try{
            method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
        }catch (NoSuchMethodException e){
            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service,rpcRequest.getParameters());
    }

}
