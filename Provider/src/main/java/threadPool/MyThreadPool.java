package threadPool;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.threadpool.ThreadPool;

import java.util.concurrent.Executor;

public class MyThreadPool implements ThreadPool {
    @Override
    public Executor getExecutor(URL url) {
        return null;
    }
}
