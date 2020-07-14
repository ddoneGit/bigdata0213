package com.ddone;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/7/11-09:39
 */
public class EsWriterBulk {
    public static void main(String[] args) throws IOException {
        //1.创建工厂
        JestClientFactory jestClientFactory = new JestClientFactory();
        //2.创建配置信息
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder("http://hadoop102:9200").build();
        //3.设置配置信息
        jestClientFactory.setHttpClientConfig(httpClientConfig);
        //4.获取客户端对象
        JestClient jestClient = jestClientFactory.getObject();
        //5.创建多个Index对象
        StuBean stu1 = new StuBean("aaa", 16L);
        StuBean stu2 = new StuBean("bbb", 16L);
        StuBean stu3 = new StuBean("ccc", 16L);
        Index index1 = new Index.Builder(stu1).id("1005").build();
        Index index2 = new Index.Builder(stu2).id("1006").build();
        Index index3 = new Index.Builder(stu3).id("1007").build();
        //6.创建Bulk对象 创建多个index对象
        Bulk bulk = new Bulk.Builder()
                .addAction(index1)
                .addAction(index2)
                .addAction(index3)
                .defaultIndex("stu")
                .defaultType("_doc")
                .build();
        //7.执行批量操作
        jestClient.execute(bulk);
        //8.释放连接
        jestClient.close();
        //生产环境中用for循环,对接流式数据
    }
}
