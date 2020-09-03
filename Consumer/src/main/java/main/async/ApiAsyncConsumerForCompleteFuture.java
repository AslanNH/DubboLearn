package main.async;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.remoting.exchange.ResponseCallback;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.dubbo.FutureAdapter;
import service.GreetingService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * FutureAdapter设置future的回调函数，
 * 当future准备好的时候，则会回调done方法。
 * 出现异常则回调 caught
 */
public class ApiAsyncConsumerForCompleteFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-async-callback-consumer"));
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

        CompletableFuture completableFuture = RpcContext.getContext().getCompletableFuture();
        completableFuture.whenComplete((v, t) ->{
                if(t != null) ((Throwable)t).printStackTrace();
                else System.out.println(v);
        });

        System.out.println("over");
        Thread.currentThread().join();//为了等待返回结果
    }
}
