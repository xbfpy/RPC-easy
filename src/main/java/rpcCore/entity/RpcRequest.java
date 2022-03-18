package rpcCore.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 86132
 * @create 2022/1/1 21:44
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
