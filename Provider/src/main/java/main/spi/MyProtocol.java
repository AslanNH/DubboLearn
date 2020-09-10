package main.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.RpcException;

public class MyProtocol implements Protocol {
    @Override
    public int getDefaultPort() {
        System.out.println("method: getDefaultPort");
        return 0;
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        System.out.println("method: export");
        return null;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> aClass, URL url) throws RpcException {
        System.out.println("method: refer");
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("method: destroy");
    }
}
