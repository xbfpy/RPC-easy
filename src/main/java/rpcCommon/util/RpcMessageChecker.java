package rpcCommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCommon.enumeration.ResponseCode;
import rpcCommon.enumeration.RpcError;
import rpcCommon.exception.RpcException;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;

/**
 * @author 86132
 * @create 2022/3/19 21:39
 */
public class RpcMessageChecker {
    public static final String INTERFACE_NAME = "interfaceName";
    private static final Logger logger = LoggerFactory.getLogger(RpcMessageChecker.class);

    private RpcMessageChecker(){

    }

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse){
        if (rpcResponse==null){
            logger.error("调用服务失败,serviceName:{}",rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,INTERFACE_NAME+":"+rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())){
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH,INTERFACE_NAME+":"+rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getStatusCode()==null||!rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())){
            logger.error("调用服务失败,serviceName:{},RpcResponse:{}",rpcRequest.getInterfaceName(),rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE,INTERFACE_NAME+":"+rpcRequest.getInterfaceName());
        }
    }
}
