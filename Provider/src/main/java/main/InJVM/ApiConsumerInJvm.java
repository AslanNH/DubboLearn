package main.InJVM;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingService;
import service.impl.GreetingServiceImpl;

public class ApiConsumerInJvm {

    public static void exportService(){
        ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("in-jvm-provider"));
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        serviceConfig.setInterface(GreetingService.class);
        serviceConfig.setRef(new GreetingServiceImpl());
        serviceConfig.setGroup("dubbo");
        serviceConfig.setVersion("1.0.0");

        serviceConfig.export();

        System.out.println("server is started");

    }

    public static void referService(){
        ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface(GreetingService.class);
        referenceConfig.setGroup("dubbo");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setCheck(false);
        referenceConfig.setTimeout(5000);

        GreetingService greetingService = referenceConfig.get();
        RpcContext.getContext().setAttachment("company","alibaba");
        System.out.println(greetingService.sayHello("world"));
    }

    public static void main(String[] args) throws InterruptedException {
        exportService();
        referService();
        Thread.currentThread().join();
    }
}
