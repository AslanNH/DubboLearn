package main.async;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ApiAsyncConsumer {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-async-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        //异步调用需要设置超时是时间，（默认超时时间1s）不然服务处理时间过长，消费者将断去链接，导致future.get()报错
        referenceConfig.setTimeout(5000);


        referenceConfig.setAsync(true);

        GreetingService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHello(" world"));

        Future<String> future = RpcContext.getContext().getFuture();
        //调用get会阻塞
        System.out.println(future.get(5, TimeUnit.SECONDS));
    }
}
