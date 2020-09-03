package service.impl.async;

import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingServiceRpcContext;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GreetingServiceAsyncContextImpl implements GreetingServiceRpcContext {

    private final ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue(), new NamedThreadFactory("biz-thread-pool"), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public String sayHello(String name) {
        final AsyncContext asyncContext = RpcContext.startAsync();
        bizThreadPool.execute(()->{
            asyncContext.signalContextSwitch();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asyncContext.write("Hello " + name +" "+ RpcContext.getContext().getAttachment("company"));
        });
        return null;
    }
}
