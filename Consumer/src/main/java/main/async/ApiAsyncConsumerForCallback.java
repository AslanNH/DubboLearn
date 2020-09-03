package main.async;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.remoting.exchange.ResponseCallback;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.dubbo.FutureAdapter;
import service.GreetingService;

import javax.security.auth.callback.CallbackHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * FutureAdapter设置future的回调函数，
 * 当future准备好的时候，则会回调done方法。
 * 出现异常则回调 caught
 */
public class ApiAsyncConsumerForCallback {

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

        ((FutureAdapter)RpcContext.getContext().getFuture()).getFuture().setCallback(new ResponseCallback(){

            @Override
            public void done(Object o) {
                System.out.println("result: " + o);
            }

            @Override
            public void caught(Throwable throwable) {
                System.out.println("error: " + throwable.getLocalizedMessage() );
            }
        });
        System.out.println("over");
        Thread.currentThread().join();//为了等待返回结果
    }
}
