package second

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/8/9-21:13
 *
 */
object SocketWordCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SocketWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.socketTextStream("localhost",9999)
      .flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .print()
    ssc.start()
    ssc.awaitTermination()
  }
}
