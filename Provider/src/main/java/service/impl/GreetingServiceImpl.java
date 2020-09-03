package service.impl;

import com.google.gson.JsonObject;
import entity.Pojo;
import entity.Result;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.rpc.RpcContext;
import service.GreetingService;

import java.io.IOException;

public class GreetingServiceImpl implements GreetingService {

    @Override
    public String sayHello(String name) {
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //RpcContext.getContext().getAttachment("company")获取调用方在上下文对象中设置的company属性，
        // 如果没有设置返回null
        return "Hello" + name + " " + RpcContext.getContext().getAttachment("company");
    }

    @Override
    public Result<String> testGeneric(Pojo pojo) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        try {
            result.setData(JSON.json(pojo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
