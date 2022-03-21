package model.client;

import rpcCore.entity.RpcRequest;

/**
 * @author 86132
 * @create 2022/3/18 21:21
 */
public interface RpcClient {
    public Object sendRequest(RpcRequest rpcRequest);
}
