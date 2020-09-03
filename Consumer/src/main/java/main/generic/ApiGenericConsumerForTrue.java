package main.generic;

import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiGenericConsumerForTrue {

    public static void main(String[] args) throws IOException {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("true-generic-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        referenceConfig.setInterface("service.GreetingService");
        referenceConfig.setTimeout(5000);
        //泛化引用，类型为true
        referenceConfig.setGeneric(true);

        GenericService genericService = referenceConfig.get();

        //泛型调用，基本类型以及Date、List、Map等不需要转换，直接调用，如果返回值是POJO，也净自动转为Map
        Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {"world"});

        System.out.println(JSON.json(result));

        //POJO参数转换为MAP
        Map<String, Object> map = new HashMap<>();
        map.put("class","entity.Pojo");
        map.put("id","1990");
        map.put("name","linjunjie");

        result = genericService.$invoke("testGeneric",new String[]{"entity.Pojo"},new Object[]{map});
        System.out.println(result);
    }
}
