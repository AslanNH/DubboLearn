package main;

import org.apache.dubbo.config.*;
import service.GreetingService;
import service.impl.GreetingServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 发布服务到zk
 */
public class ApiProvider {

    public static void main(String[] args) throws  IOException {
        //1.构建ServiceConfig对象
        ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));

        //2.构建注册地址
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");

        //3.指定当前服务的注册中心地址
        serviceConfig.setRegistry(registryConfig);

        //4.设置要暴露的接口和实现类
        serviceConfig.setInterface(GreetingService.class);
        serviceConfig.setRef(new GreetingServiceImpl());
        //serviceConfig.setProtocol(new ProtocolConfig("myprotocol"));
        //5.dubbo中 服务接口+服务分组+服务版本唯一的确定一个服务
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("dubbo");

        //设置并发限制个数---针对所有方法
        serviceConfig.setExecutes(10);
        //设置并发限制个数---针对特定方法
        /*final List<MethodConfig> methodConfigs = new ArrayList<>();
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setActives(10);
        methodConfig.setName("sayHello");
        methodConfigs.add(methodConfig);
        serviceConfig.setMethods(methodConfigs);*/
        //6.设置线程池策略
        /*HashMap<String,String> parameters = new HashMap<>();
        parameters.put("threadpool","myThreadPool");
        serviceConfig.setParameters(parameters);*/
        //6.设置线程模型
        /*HashMap<String,String> parameters = new HashMap<>();
        parameters.put("dispatcher","myDispatcher");
        serviceConfig.setParameters(parameters);*/
        //7.启动NettyServer监听链接请求，并将服务注册到注册中心
        serviceConfig.export();

        System.out.println(">>>>>>>>>>service is start<<<<<<<<<<");
       System.in.read();
    }
}
