package model.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpcCore.entity.RpcRequest;
import rpcCore.entity.RpcResponse;
import rpcCore.handler.RequestHandler;
import rpcCore.register.ServiceRegistry;
import rpcCore.workthread.RequestHandlerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author 86132
 * @create 2022/1/2 22:23
 */
public class RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_LIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ServiceRegistry registry;
    private RequestHandler requestHandler;
    private final ExecutorService threadPool;

    public RpcServer(ServiceRegistry registry){
        this.registry = registry;
        requestHandler = new RequestHandler();
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory factory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_LIVE_TIME,TimeUnit.SECONDS,workingQueue,factory);
    }

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("服务器启动...");
            Socket socket;
            while((socket = serverSocket.accept())!=null){
                logger.info("消费者连接: {}:{}",socket.getInetAddress(),socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket,requestHandler,registry));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动发生错误:",e);
        }
    }

//    public void register(Object service,int port){
//        try(ServerSocket serverSocket = new ServerSocket(port)){
//            logger.info("服务器正在启动..");
//            Socket socket;
//            while((socket = serverSocket.accept())!=null){
//                logger.info("客户端已连接，IP："+socket.getInetAddress());
//                threadPool.execute(new WorkerThread(socket,service));
//            }
//        } catch (IOException e) {
//            logger.error("注册时有错误发生：",e);
//        }
//    }
//    private class WorkerThread extends Thread{
//
//        private Socket socket;
//        private Object service;
//        public WorkerThread(Socket socket,Object service){
//            this.socket = socket;
//            this.service = service;
//        }
//        @Override
//        public void run(){
//            try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
//                RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();
//                Method method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
//                Object returnObject = method.invoke(service,rpcRequest.getParameters());
//                objectOutputStream.writeObject(RpcResponse.success(returnObject));
//                objectOutputStream.flush();
//            } catch (IOException | ClassNotFoundException | NoSuchMethodException |InvocationTargetException| IllegalAccessException e) {
//                logger.error("调用或发送时有错误发生:",e);
//                e.printStackTrace();
//            }
//        }
//    }
}
