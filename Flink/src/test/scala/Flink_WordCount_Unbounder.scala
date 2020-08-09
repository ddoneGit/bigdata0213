import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/8/3-12:34
 *
 */
object Flink_WordCount_Unbounder {
  def main(args: Array[String]): Unit = {
    //获取环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(2)
    //读取流
    val lineDS: DataStream[String] = env.socketTextStream("hadoop102", 9999)
    //扁平化
    val wordDS: DataStream[String] = lineDS.flatMap(_.split(" "))
    //转换为元祖
    val wordToOneDS: DataStream[(String, Int)] = wordDS.map((_, 1))
    //分组
    val keyDS: KeyedStream[(String, Int), Tuple] = wordToOneDS.keyBy(0)
    //聚合
    val resultDS: DataStream[(String, Int)] = keyDS.sum(1)
    //打印
    resultDS.print()
    //执行
    env.execute()
  }

}

