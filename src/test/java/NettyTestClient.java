import model.HelloObject;
import model.client.NettyClient;
import model.client.RpcClient;
import rpcCore.proxy.RpcClientProxy;
import service.HelloService;

/**
 * @author 86132
 * @create 2022/3/19 21:22
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1",9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12,"This is message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
