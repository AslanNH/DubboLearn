package main.async;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import service.AsyncGreetingService;
import service.GreetingService;

import java.util.concurrent.CompletableFuture;

public class ApiConsumerForProviderAsync {

    public static void main(String[] args) {
        ReferenceConfig<AsyncGreetingService> referenceConfig = new ReferenceConfig<>();

        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        referenceConfig.setRegistry(registryConfig);

        referenceConfig.setInterface(AsyncGreetingService.class);
        referenceConfig.setTimeout(10000);

       /* referenceConfig.setLoadbalance("myLoadBalance");
        referenceConfig.setCluster("myBoradcast");*/

       referenceConfig.setVersion("1.0.0");
       referenceConfig.setGroup("dubbo");

        AsyncGreetingService greetingService = referenceConfig.get();

       //设置隐式参数
        RpcContext.getContext().setAttachment("company","alibaba");

        CompletableFuture<String> future = greetingService.sayHello("world");
        future.whenComplete((v,t)->{
            if(t != null) ((Throwable)t).printStackTrace();
            else System.out.println(v);
        });
        System.out.println("over");
    }
}
