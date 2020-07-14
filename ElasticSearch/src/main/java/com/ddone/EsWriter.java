package com.ddone;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/7/11-09:15
 */
//单条数据的写入操作
public class EsWriter {
    public static void main(String[] args) throws IOException {
        //1.创建工程
        JestClientFactory jestClientFactory = new JestClientFactory();
        //2.创建配置信息
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder("http://hadoop102:9200").build();
        //3.设置配置信息
        jestClientFactory.setHttpClientConfig(httpClientConfig);
        //4.获取jestClient
        JestClient jestClient = jestClientFactory.getObject();
        //5.创建Index对象
//        Index index = new Index.Builder("{\n" +
//                "  \"name\":\"lishi\",\n" +
//                "  \"age\":13\n" +
//                "}").index("stu").type("_doc").id("1003").build();
        //也可以用Bean对象
        StuBean stu1 = new StuBean("wangwu", 15L);
        Index index = new Index.Builder(stu1).index("stu").type("_doc").id("1004").build();
        /*

        source 可以用Javabean类型替换
         */
        //6.触发操作
        jestClient.execute(index);
        //7.释放连接
        jestClient.close();

    }
}
