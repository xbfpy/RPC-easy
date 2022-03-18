package rpcCore.entity;

import lombok.Data;
import rpcCommon.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * @author 86132
 * @create 2022/1/1 21:46
 */
@Data
public class RpcResponse<T> implements Serializable {
    private Integer statusCode;
    private String message;
    private T data;
    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<T>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }
    public static <T> RpcResponse<T> fail(T data){
        RpcResponse<T> response = new RpcResponse<T>();
        response.setStatusCode(ResponseCode.FAIL.getCode());
        response.setData(data);
        return response;
    }

}
