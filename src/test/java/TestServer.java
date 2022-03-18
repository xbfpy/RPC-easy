import model.server.RpcServer;
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
        RpcServer rpcServer = new RpcServer(registry);
        rpcServer.start(9000);
    }
}
