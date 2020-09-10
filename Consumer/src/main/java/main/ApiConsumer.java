package main;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MethodConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingService;

import java.util.ArrayList;
import java.util.List;

public class ApiConsumer {

    public static void main(String[] args) {
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();

        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        referenceConfig.setRegistry(registryConfig);

        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setTimeout(5000);

        //设置并发限制个数---针对所有方法
        //referenceConfig.setActives(10);
        //设置并发限制个数---针对特定方法
        final List<MethodConfig> methodConfigs = new ArrayList<>();
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setActives(10);
        methodConfig.setName("sayHello");
        methodConfigs.add(methodConfig);
        referenceConfig.setMethods(methodConfigs);
       /* referenceConfig.setLoadbalance("myLoadBalance");
        referenceConfig.setCluster("myBoradcast");*/


       //referenceConfig.setCluster("myCluster");
       referenceConfig.setVersion("1.0.0");
       referenceConfig.setGroup("dubbo");

       GreetingService greetingService = referenceConfig.get();

       //设置隐式参数
        RpcContext.getContext().setAttachment("company","alibaba");

        //发起调用，阻塞直到有返回
        System.out.println(greetingService.sayHello(" world"));
    }
}
