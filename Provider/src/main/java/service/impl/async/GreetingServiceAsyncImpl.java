package service.impl.async;

import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.RpcContext;
import service.AsyncGreetingService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GreetingServiceAsyncImpl implements AsyncGreetingService {

    private final ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(8, 16, 1, TimeUnit.MINUTES,
            new SynchronousQueue(), new NamedThreadFactory("biz-thread-pool"), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public CompletableFuture<String> sayHello(String name) {
        RpcContext context = RpcContext.getContext();

        //通过supplyAsunc开启异步执行
        return CompletableFuture.supplyAsync(()-> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("async return ");
            return "Hello " + name + " " + context.getAttachment("company");
        }, bizThreadPool);

    }
}
