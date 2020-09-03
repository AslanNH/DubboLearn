package main.async;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingService;
import service.GreetingServiceRpcContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ApiAsyncConsumerForAsyncContext {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ReferenceConfig<GreetingServiceRpcContext> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-async-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(GreetingServiceRpcContext.class);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        //异步调用需要设置超时是时间，（默认超时时间1s）不然服务处理时间过长，消费者将断去链接，导致future.get()报错
        referenceConfig.setTimeout(10000);
        //referenceConfig.setAsync(true);
        GreetingServiceRpcContext greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello(" world"));
        Future<String> future = RpcContext.getContext().getFuture();
        System.out.println(future.get());
        System.out.println("over");

    }
}
