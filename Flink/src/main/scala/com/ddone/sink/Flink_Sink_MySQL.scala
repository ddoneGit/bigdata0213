package com.ddone.sink

import java.sql.{Connection, DriverManager, PreparedStatement}

import com.ddone.bean.SensorReading
import com.ddone.source.Flink_Source_MySource.MySource
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink._
import org.apache.flink.streaming.api.scala._

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/29-14:26
 *
 */
object Flink_Sink_MySQL {
  def main(args: Array[String]): Unit = {
    //1.创建环境对象
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //2.读取数据源
    val sourceDS: DataStream[SensorReading] = env.addSource(new MySource)
    //3.写入MySQL
    sourceDS.addSink(new MySQLSink)
    //4.执行
      sourceDS.print("Sensor")
      env.execute("Flink_Sink_MySQL")

  }

  //实现RichSinkFunction
  class MySQLSink() extends RichSinkFunction[SensorReading]{
    var connection : Connection = _
    var insertPre: PreparedStatement= _
    var updatePre: PreparedStatement = _
    override def open(parameters: Configuration): Unit = {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "abc123")
      insertPre = connection.prepareStatement("insert into sensor values(?,?)")
      updatePre = connection.prepareStatement("update  sensor set temperature = ? where id = ?")
  //      创建连接
    }
    override def invoke(value: SensorReading, context: SinkFunction.Context[_]): Unit = {
      //使用连接
      updatePre.setDouble(1,value.temperature)
      updatePre.setString(2,value.id)
      updatePre.execute()
      if (updatePre.getUpdateCount == 0){
        insertPre.setString(1,value.id)
        insertPre.setDouble(2,value.temperature)
        insertPre.execute()
      }
    }

    override def close(): Unit = {
      //关闭连接
      updatePre.close()
      insertPre.close()
      connection.close()

    }
  }
}
