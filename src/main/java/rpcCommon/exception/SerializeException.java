package rpcCommon.exception;

import rpcCommon.enumeration.RpcError;

/**
 * @author 86132
 * @create 2022/3/23 19:57
 */
public class SerializeException extends RuntimeException{
    public SerializeException(RpcError error){
        super(error.getMessage());
    }
    public SerializeException(RpcError error,String details){
        super(error.getMessage()+":"+details);
    }
}
