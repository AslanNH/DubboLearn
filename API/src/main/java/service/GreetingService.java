package service;

import entity.Pojo;
import entity.Result;

/**
 * 同步接口
 */
public interface GreetingService {
    String sayHello(String name);

    Result<String> testGeneric(Pojo pojo);
}
