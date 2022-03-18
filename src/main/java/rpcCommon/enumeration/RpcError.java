package rpcCommon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 86132
 * @create 2022/3/17 21:35
 */
@Getter
@AllArgsConstructor
public enum RpcError {
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("该类没有任何实现服务"),
    SERVICE_NOT_FOUND("未找到该服务");
    private String message;
}
