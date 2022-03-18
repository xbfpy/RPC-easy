import model.HelloObject;
import rpcCore.entity.RpcResponse;
import rpcCore.proxy.RpcClientProxy;
import service.HelloService;

/**
 * @author 86132
 * @create 2022/1/3 14:02
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy clientProxy = new RpcClientProxy("127.0.0.1",9000);
        HelloService helloService = clientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(1,"hello world!");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
