package main.async;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import service.GreetingServiceRpcContext;
import service.impl.async.GreetingServiceAsyncContextImpl;

import java.io.IOException;

/**
 * 发布服务到zk
 */
public class ApiProviderForAsyncContext {

    public static void main(String[] args) throws  IOException {
        //1.构建ServiceConfig对象
        ServiceConfig<GreetingServiceRpcContext> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));

        //2.构建注册地址
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");

        //3.指定当前服务的注册中心地址
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setAsync(true);
        //4.设置要暴露的接口和实现类
        serviceConfig.setInterface(GreetingServiceRpcContext.class);
        serviceConfig.setRef(new GreetingServiceAsyncContextImpl());

        //5.dubbo中 服务接口+服务分组+服务版本唯一的确定一个服务
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("dubbo");

        //6.设置线程池策略
        /*HashMap<String,String> parameters = new HashMap<>();
        parameters.put("threadpool","mythreadpool");
        serviceConfig.setParameters(parameters);*/

        //7.启动NettyServer监听链接请求，并将服务注册到注册中心
        serviceConfig.export();

        System.out.println(">>>>>>>>>>service is start<<<<<<<<<<");
       System.in.read();
    }
}
