package service;

import entity.Pojo;
import entity.Result;
import service.GreetingService;

/**
 * 改mock类必须与接口的包类名一致，不然找不到
 *
 */
public class GreetingServiceMock implements GreetingService {

    @Override
    public String sayHello(String name) {
        return "mock value";
    }

    @Override
    public Result<String> testGeneric(Pojo pojo) {
        return null;
    }
}
