package rpcCore.serializer;

/**
 * @author 86132
 * @create 2022/3/19 20:33
 */
public interface CommonSerializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes,Class<?> clazz);
    int getCode();
    static CommonSerializer getByteCode(int code){
        switch (code){
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
