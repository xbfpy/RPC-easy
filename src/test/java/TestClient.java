import model.HelloObject;
import rpcCore.entity.RpcResponse;
import rpcCore.proxy.RpcClientProxy;
import service.HelloService;
import service.Impl.HelloServiceImpl;

/**
 * @author 86132
 * @create 2022/1/3 14:02
 */
public class TestClient {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloObject object = new HelloObject(1,"hello world!");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
