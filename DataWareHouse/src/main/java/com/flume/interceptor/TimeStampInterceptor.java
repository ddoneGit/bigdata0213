package com.flume.interceptor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ddone
 * @date 2020/6/24-15:25
 */
public class TimeStampInterceptor implements Interceptor {
    private List<Event> result = new ArrayList<Event>();
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //1.获取数据的头信息和Body信息
        Map<String, String> headers = event.getHeaders();
        byte[] body = event.getBody();
        String bodyStr = new String(body);
        //2.将字符串转换为JSon对象
        JSONObject jsonObject = JSON.parseObject(bodyStr);
        //3.获取数据中的时间戳
        String ts = jsonObject.getString("ts");
        //4.用数据中的时间戳替换Header中的时间戳
        headers.put("timestamp",ts);
        //5.返回数据
        return event;

    }

    @Override
    public List<Event> intercept(List<Event> events) {
        //批量处理数据
        //① 清空集合
        result.clear();
        for (Event event : events) {
            result.add(intercept(event));
        }
        return result;
    }

    @Override
    public void close() {

    }
    //静态内部类Builder
    public static class Builder implements Interceptor.Builder{
        @Override
        public Interceptor build() {
            return  new TimeStampInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
