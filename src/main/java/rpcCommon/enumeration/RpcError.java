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
    SERVICE_NOT_FOUND("未找到该服务"),
    UNKNOWN_PROTOCOL("未知的协议"),
    UNKNOWN_PACKAGE_TYPE("未知的数据包"),
    UNKNOWN_SERIALIZER("不识别的反序列化器"),
    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    RESPONSE_NOT_MATCH("没有匹配的请求"),
    SERIALIZE_ERROR("序列化失败"),
    DESERIALIZE_ERROR("反序列化失败");
    private String message;
}
