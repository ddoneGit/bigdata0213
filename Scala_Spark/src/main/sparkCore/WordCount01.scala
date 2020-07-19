import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 **
@author ddone
 **
 * @date 2020/7/16-10:59
 *
 */
object WordCount01 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)
    val dataRDD: RDD[String] = sc.textFile("input/word.txt")

    println(dataRDD.collect().mkString(","))
    sc.textFile("input/word.txt")
      .flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .foreach(println)
  }

}
