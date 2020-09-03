package main.mock;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingService;

public class ApiConsumerMock {

    public static void main(String[] args) {
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("mock-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setTimeout(5000);
        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");

        referenceConfig.setCheck(false);
        referenceConfig.setMock(true);

        GreetingService greetingService = referenceConfig.get();

        RpcContext.getContext().setAttachment("company","alibaba");

        System.out.println(greetingService.sayHello("world"));
    }
}
