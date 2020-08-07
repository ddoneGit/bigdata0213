package com.ddone.sink

import java.util

import com.ddone.bean.SensorReading
import com.ddone.source.Flink_Source_MySource.MySource
import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.http.HttpHost
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSinkFunction, RequestIndexer}
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Requests

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/29-14:40
 *
 */
object Flink_Sink_ES {
  def main(args: Array[String]): Unit = {
    //1.获取环境对象
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //2.获取数据源
    val sourceDS: DataStream[SensorReading] = env.addSource(new MySource)
    //3.写入ES
    val hostList = new util.ArrayList[HttpHost]()
    hostList.add(new HttpHost("hadoop102",9200))
    val esSinkBuilder = new ElasticsearchSink.Builder[SensorReading](hostList, new MyESSinkFunction)
//      .setBulkFlushMaxActions(1)
    sourceDS.addSink(esSinkBuilder.build())
    //4.执行
    env.execute()
  }
  class  MyESSinkFunction extends ElasticsearchSinkFunction[SensorReading]{

    override def process(element: SensorReading, ctx: RuntimeContext, indexer: RequestIndexer): Unit = {
      val json = new util.HashMap[String,String]()
      json.put("data",element.toString)
      val rqst: IndexRequest = Requests.indexRequest()
        .index("FlinkTest")
        .`type`("_doc")
        .source(json)
      indexer.add(rqst)
      println(element.toString)
      println("测试结束")
    }
  }
}
