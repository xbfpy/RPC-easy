package model.server;

import rpcCore.register.ServiceRegistry;


/**
 * @author 86132
 * @create 2022/3/18 21:23
 */
public interface RpcServer {
    void start(int port, ServiceRegistry registry);
}
