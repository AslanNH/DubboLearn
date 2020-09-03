package main.generic;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.io.UnsafeByteArrayInputStream;
import org.apache.dubbo.common.io.UnsafeByteArrayOutputStream;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.io.IOException;

public class ApiGenericConsumerForNativeJava {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("true-generic-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGroup("dubbo");
        referenceConfig.setInterface("service.GreetingService");
        referenceConfig.setTimeout(5000);
        //泛化引用，类型为true
        referenceConfig.setGeneric("nativejava");

        GenericService genericService = referenceConfig.get();

        //泛型调用，参数使用JavaBean进行序列化
        UnsafeByteArrayOutputStream out = new UnsafeByteArrayOutputStream();
        ExtensionLoader.getExtensionLoader(Serialization.class)
                .getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA)
                .serialize(null, out)
                .writeObject("world");
        Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {out.toByteArray()});
        UnsafeByteArrayInputStream in  = new UnsafeByteArrayInputStream((byte[])result);
        System.out.println(ExtensionLoader.getExtensionLoader(Serialization.class)
        .getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA)
        .deserialize(null, in)
        .readObject());
/*
        //POJO参数转换为MAP
        UnsafeByteArrayOutputStream out = new UnsafeByteArrayOutputStream();
        Pojo pojo = new Pojo();
        pojo.setId("abcdefg");
        pojo.setName("七月的风");
        ExtensionLoader.getExtensionLoader(Serialization.class)
                .getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA)
                .serialize(null, out)
                .writeObject(pojo);
        Object result = genericService.$invoke("testGeneric",new String[]{"entity.Pojo"},new Object[]{out.toByteArray()});
        UnsafeByteArrayInputStream in  = new UnsafeByteArrayInputStream((byte[])result);
        System.out.println(ExtensionLoader.getExtensionLoader(Serialization.class)
                .getExtension(Constants.GENERIC_SERIALIZATION_NATIVE_JAVA)
                .deserialize(null, in)
                .readObject());*/
    }
}
