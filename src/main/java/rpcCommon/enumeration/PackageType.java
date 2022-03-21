package rpcCommon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 86132
 * @create 2022/3/19 20:11
 */
@Getter
@AllArgsConstructor
public enum  PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
