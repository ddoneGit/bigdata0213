package com.ddone.source

import java.util.Random

import com.ddone.bean.SensorReading
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala._

import scala.collection.immutable

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/28-16:44
 *
 */
object Flink_Source_MySource {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val sensorDS: DataStream[SensorReading] = env.addSource(new MySource);
    sensorDS.print()
    env.execute("Flink_Source_MySource")
  }

   class MySource extends SourceFunction[SensorReading]{
     var running = true
     override def run(sourceContext: SourceFunction.SourceContext[SensorReading]): Unit = {
       val random = new Random()
       val firstTemp: immutable.IndexedSeq[(String, Double)] = (1 to 5).map(
         i => (s"Sensor_$i", 30 + random.nextGaussian())
       )
       while (running){
         val sensorReadings: immutable.IndexedSeq[SensorReading] = firstTemp.map(
           t => {
             SensorReading(t._1, System.currentTimeMillis(), t._2+random.nextGaussian()*5)
           }
         )
         sensorReadings.foreach(
           sensorReading=> sourceContext.collect(sensorReading)
         )
         Thread.sleep(5000)
       }
     }

     override def cancel(): Unit = {
       running = false
     }
   }

}
