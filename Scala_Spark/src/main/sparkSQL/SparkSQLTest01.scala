import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 **
@author ddone
 **
 * @date 2020/7/16-15:25
 *
 */
object SparkSQLTest01 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SQL")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
    val df: DataFrame = spark.read.json("input/jsonFile.json")
      df.createOrReplaceTempView("user")
//    spark.sql(
//      """
//        |select
//        |*
//        |from
//        |user
//        |""".stripMargin).show()
//    df.show()

//    spark.sql("select * from user where sex >25").show()
//    spark.newSession().sql("select * from user").show(5)//报错,因为是临时表
//    df.createGlobalTempView("emp")
//    spark.newSession().sql("select * from global_temp.emp").show(5)//需要加上global_temp
//    dslTest(df)
//    df.select('age+1 as "newge",'name).show
//    df.select($"age"+1,$"sex").show(5)
//    df.filter('age>20).show(3)

    /**
     *
     * RDD转换为DF,toDF,rdd
     */
//    spark.sparkContext.makeRDD(List(1,2,3,4)).toDF("num").show()
//    println(df.rdd.collect().mkString(","))

//    spark.sparkContext.makeRDD(List((1,"zhangsan",20),(2,"lishi",30))).toDF("id","name","age").show()
//val dataRDD: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1, "zhangsan", 20), (2, "lishi", 30)))

//    dataRDD.map(t=>userRDD(t._1,t._2,t._3)).toDF().show()//不用写列名

//    dataRDD.map(t=>userRDD(t._1,t._2,t._3)).toDF().rdd.foreach(println)

    /*
    DF-->DS df.as[case class]
    ds.toDF
    ds.rdd
    rdd.toDS rdd需转换为样列类型的
     */
//    df.show()
//    val rdd: RDD[Row] = df.rdd
//    rdd.foreach(println)
//    rdd.map(t => tRDD())
val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1, "zhangsan", 20), (2, "lishi", 30)))
    val ds: Dataset[UserRDD] = rdd.map(t => UserRDD(t._1, t._2, t._3)).toDS()// rdd --> ds
    val df1: DataFrame = rdd.map(t => UserRDD(t._1, t._2, t._3)).toDF//rdd -->df
//    df1.show()
//    ds.toDF().show()//
  df1.as[UserRDD].show() //df -->ds

  spark.close()
  }
  def dslTest(df:DataFrame): Unit ={
//    df.show()
    df.select("age").show(6)
    println("=======================")
    df.select("age","name").show()

  }
  case  class UserRDD(sex:Int, name:String, age:BigInt)
//  case  class tRDD(age:Int,name:String,sex:String)
}
