import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 **
@author ddone
 **
 * @date 2020/7/16-22:22
 *
 */
object  MySQL_HiveConnect {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("mysql&hive")
    val spark: SparkSession = SparkSession.builder()
      .config(conf)
      .enableHiveSupport()//增加Hive支持
      .getOrCreate()
    System.setProperty("HADOOP_USER_NAME", "root")
    import spark.implicits._
    val df: DataFrame = spark.read.format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", "jdbc:mysql://hadoop102:3306/ddone")
      .option("user", "root")
      .option("password", "abc123")
      .option("dbtable","emp")
      .load()
//    df.show()
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext
      .makeRDD(List((5, "wangwu", 30), (6, "zhaoliu", 25), (7, "qangqie", 35)))
    val df1: DataFrame = rdd.map(t => Emp(t._1, t._2, t._3)).toDF()
    df1.show()
    df1.write
        .mode("overwrite")
      .format("jdbc")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("url", "jdbc:mysql://hadoop102:3306/ddone")
      .option("user", "root")
      .option("password", "abc123")
      .option("dbtable","emp1")
//      .save()
    //====================Hive=============
    spark.sql("show tables").show


  }
  case class Emp(id:Int,name:String,age:Int)

}
