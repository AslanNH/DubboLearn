package service;

import java.util.concurrent.CompletableFuture;

/**
 * 异步接口
 */
public interface AsyncGreetingService {
    CompletableFuture<String> sayHello(String name);
}
