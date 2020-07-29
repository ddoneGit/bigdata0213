package com.ddone.transform

import org.apache.flink.api.common.functions.{RichMapFunction, RuntimeContext}
import org.apache.flink.streaming.api.scala._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-20:56
 *
 */
object Flink_Transform_Map {
  def main(args: Array[String]): Unit = {
    //1.获取环境对象
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //2.获取端口数据
    val socketDS: DataStream[String] = env.socketTextStream("localhost", 9999)
    //3.使用map方法
    val mapDS: DataStream[String] = socketDS.map(new MyMapFunction)
    //4.打印执行
    mapDS.print()
    env.execute("Flink_Transform_Map")
  }

  //创建RichMapFunction的子类
  class MyMapFunction extends RichMapFunction[String,String]{

    override def getRuntimeContext: RuntimeContext = super.getRuntimeContext

    override def map(value: String): String = {
      value*5
    }
  }

}
