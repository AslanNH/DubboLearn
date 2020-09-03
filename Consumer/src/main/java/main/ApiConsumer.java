package main;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingService;

public class ApiConsumer {

    public static void main(String[] args) {
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();

        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        referenceConfig.setRegistry(registryConfig);

        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setTimeout(5000);

       /* referenceConfig.setLoadbalance("myLoadBalance");
        referenceConfig.setCluster("myBoradcast");*/

       referenceConfig.setVersion("1.0.0");
       referenceConfig.setGroup("dubbo");

       GreetingService greetingService = referenceConfig.get();

       //设置隐式参数
        RpcContext.getContext().setAttachment("company","alibaba");

        //发起调用，阻塞直到有返回
        System.out.println(greetingService.sayHello(" world"));
    }
}
