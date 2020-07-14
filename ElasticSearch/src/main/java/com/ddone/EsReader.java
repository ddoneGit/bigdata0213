package com.ddone;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ddone
 * @date 2020/7/11-09:16
 */
public class EsReader {
    public static void main(String[] args) throws IOException {
        //1.创建工程
        JestClientFactory jestClientFactory = new JestClientFactory();
        //2.创建配置信息
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder("http://hadoop102:9200").build();
        //3.设置配置信息
        jestClientFactory.setHttpClientConfig(httpClientConfig);
        //4.获取jestClient
        JestClient jestClient = jestClientFactory.getObject();
        //5.创建Search对象
        Search search = new Search.Builder("{  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"filter\": {\n" +
                "        \"term\":{\n" +
                "          \"sex\":\"male\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                " },\n" +
                "    \"aggs\":{\n" +
                "    \"group_by_age\":{\n" +
                "      \"terms\": {\n" +
                "        \"field\": \"age\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"from\":0,\n" +
                "  \"size\":5\n" +
                "}")
                .addIndex("student")
                .addType("_doc")
                .build();
        //可以用java替换
        //query 可以写很多
        //6.执行查询结果
        SearchResult searchResult = jestClient.execute(search);//需要解析结果
        //7.解析结果
        Long total = searchResult.getTotal();
        System.out.println("总数: "+total);
        System.out.println("最高分: " + searchResult.getMaxScore());
        //用Map接受数据,接收source的数据
        List<SearchResult.Hit<Map, Void>> hits = searchResult.getHits(Map.class);
        for (SearchResult.Hit<Map, Void> hit : hits) {
            Map source = hit.source;
            for (Object key : source.keySet()) {
                System.out.println(key+"  :  "+source.get(key));
            }
            System.out.println("*************************");
        }
        //获取聚合组
        MetricAggregation aggregations = searchResult.getAggregations();
        TermsAggregation group_by_sex = aggregations.getTermsAggregation("group_by_age");
        for (TermsAggregation.Entry entry : group_by_sex.getBuckets()) {
            System.out.println("Key:"+entry.getKey()+",Count"+entry.getCount());
            System.out.println("----------------------");
        }
        //读数据也可以用Bean对象
        //8.释放连接
        jestClient.close();

    }
}
