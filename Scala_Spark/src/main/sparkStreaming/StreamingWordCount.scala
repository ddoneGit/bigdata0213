import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 **
@author ddone
 **
 * @date 2020/7/16-23:12
 *
 */
object StreamingWordCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("streaming")
    val ssc = new StreamingContext(conf,Seconds(3))
    val socketDS: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)
    socketDS.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .print() //print 会打印批次信息
    ssc.start()//启动采集器
    ssc.awaitTermination()//等待采集器的结束
  }
}
