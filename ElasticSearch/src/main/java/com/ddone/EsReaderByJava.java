package com.ddone;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author ddone
 * @date 2020/7/11-10:36
 */
/*
Java方式构建DSL读取
用java代码写
可读性比较高
 */
public class EsReaderByJava {
    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        //1.创建工程
        JestClientFactory jestClientFactory = new JestClientFactory();
        //2.创建配置信息
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder("http://hadoop102:9200").build();
        //3.设置配置信息
        jestClientFactory.setHttpClientConfig(httpClientConfig);
        //4.获取jestClient
        JestClient jestClient = jestClientFactory.getObject();
        //5.创建DSL语句构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); //相当于大括号
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //定义全词匹配过滤器
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("sex", "male");
       boolQueryBuilder.filter(termQueryBuilder);
//       //定义分词匹配过滤器
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("favo", "歌");
//        boolQueryBuilder.must(matchQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        //6.创建Search对象
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex("student").addType("_doc").build();
        //7.执行查询操作
        SearchResult searchResult = jestClient.execute(search);
        //8.解析结果
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
        //9.关闭环境
        jestClient.close();
    }
}
