package dispatcher;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Dispatcher;

public class MyDispatcher implements Dispatcher {
    @Override
    public ChannelHandler dispatch(ChannelHandler channelHandler, URL url) {
        //自定义
        return null;
    }
}
