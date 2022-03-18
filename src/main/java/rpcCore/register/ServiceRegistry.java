package rpcCore.register;

/**
 * @author 86132
 * @create 2022/3/17 21:08
 */
public interface ServiceRegistry {
    <T> void register(T service);
    Object getService(String serviceName);
}
