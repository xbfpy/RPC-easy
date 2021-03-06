package rpcCommon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 86132
 * @create 2022/3/19 20:50
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {
    KRYO(0),
    JSON(1),
    HESSIAN(2);
    private final int code;
}
