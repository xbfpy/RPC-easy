package rpcCommon.exception;

import rpcCommon.enumeration.RpcError;

/**
 * @author 86132
 * @create 2022/3/17 21:47
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcError error, String detail){
        super(error.getMessage()+":"+detail);
    }
    public RpcException(RpcError error){
        super(error.getMessage());
    }
}
