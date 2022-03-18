package rpcCore.workthread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;
import rpcCore.handler.RequestHandler;
import rpcCore.register.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author 86132
 * @create 2022/3/18 19:53
 */
public class RequestHandlerThread extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry registry;

    public RequestHandlerThread(Socket socket,RequestHandler requestHandler,ServiceRegistry registry){
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.registry = registry;
    }

    @Override
    public void run(){
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = registry.getService(interfaceName);
            Object result = requestHandler.handler(rpcRequest,service);
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或者发送时有错误发生:",e);
        }
    }
}
