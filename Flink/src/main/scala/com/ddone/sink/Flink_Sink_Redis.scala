package com.ddone.sink

import com.ddone.bean.SensorReading
import com.ddone.source.Flink_Source_MySource.MySource
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.{FlinkJedisConfigBase, FlinkJedisPoolConfig}
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/29-14:40
 *
 */
object Flink_Sink_Redis {
  def main(args: Array[String]): Unit = {
    //1.获取环境对象
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    //2.添加源
    val sourceDS: DataStream[SensorReading] = env.addSource(new MySource)
    //3.写入到Redis 添加Sink
    val config: FlinkJedisPoolConfig = new FlinkJedisPoolConfig.Builder().setHost("hadoop102").setPort(6379).build()
    sourceDS.addSink(new RedisSink[SensorReading](config,new MyRedisSink))
    //4.打印执行
    sourceDS.print("Flink_Sink_Redis")
    env.execute()

  }

  class MyRedisSink extends RedisMapper[SensorReading]{
    override def getCommandDescription: RedisCommandDescription = {
      //设置执行的命令
      new RedisCommandDescription(RedisCommand.HSET,"Sensor")
    }

    override def getKeyFromData(data: SensorReading): String = {
      //redis的属性
      data.id
    }

    override def getValueFromData(data: SensorReading): String = {
      //redis的value
    data.temperature.toString
    }
  }
}
