package main.generic;

import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ApiGenericConsumerForBean {

    public static void main(String[] args) throws IOException {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("true-generic-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        referenceConfig.setInterface("service.GreetingService");
        referenceConfig.setTimeout(5000);
        //泛化引用，类型为true
        referenceConfig.setGeneric("bean");

        GenericService genericService = referenceConfig.get();

        //泛型调用，参数使用JavaBean进行序列化
        JavaBeanDescriptor param = JavaBeanSerializeUtil.serialize("world");
        Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {param});
        result = JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) result);
        System.out.println(result);
/*
        //POJO参数转换为MAP
        Map<String, Object> map = new HashMap<>();
        map.put("class","entity.Pojo");
        map.put("id","1990");
        map.put("name","linjunjie");
        param = JavaBeanSerializeUtil.serialize(map);
        result = genericService.$invoke("testGeneric",new String[]{"entity.Pojo"},new Object[]{param});
        result = JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) result);
        System.out.println(result);*/
    }
}
