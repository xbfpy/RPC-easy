package service.Impl;

import model.HelloObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HelloService;

/**
 * @author 86132
 * @create 2022/1/1 20:40
 */
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(HelloObject object) {
        logger.info("接收到:{}",object.getMessage());
        return "这时调用的返回值，id="+object.getId();
    }
}
