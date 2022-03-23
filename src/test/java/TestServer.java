import model.server.SocketRpcServer;
import rpcCore.register.DefaultServiceRegistry;
import rpcCore.register.ServiceRegistry;
import service.HelloService;
import service.Impl.HelloServiceImpl;

/**
 * @author 86132
 * @create 2022/1/3 14:01
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        SocketRpcServer SocketRpcServer = new SocketRpcServer(registry);
        SocketRpcServer.start(9000,registry);
    }
}
