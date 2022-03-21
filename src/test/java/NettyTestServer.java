import model.server.NettyServer;
import rpcCore.register.DefaultServiceRegistry;
import rpcCore.register.ServiceRegistry;
import service.HelloService;
import service.Impl.HelloServiceImpl;


/**
 * @author 86132
 * @create 2022/3/19 22:21
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9999,registry);
    }
}
