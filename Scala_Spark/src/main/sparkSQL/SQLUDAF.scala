import java.beans.Encoder

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, Row, SparkSession}
import org.apache.spark.sql.expressions.{Aggregator, MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/16-17:18
 *
 */
object SQLUDAF {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sql")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._ //必须要导入,否则无法转换 toDS,toDF
    val dm: DataFrame = spark.read.json("input/jsonFile.json")
    val myAvgUDAF = new MyAvgUDAF
    spark.udf.register("avgAge",myAvgUDAF)
    dm.show()
    dm.createOrReplaceTempView("user")
    spark.sql("select avgAge(age)  avgAge from user").show()
    spark.udf.register("changeAge",(x:Int)=>18)
    spark.udf.register("addName",(x:String) => "Name:" + x)
    spark.sql("select addName(name),changeAge(age) from user").show()
    val rdd =spark.sparkContext.makeRDD(
      List(
        (1,"zhangsan",20),
        (2,"lishi",30),
        (3,"wangwu",25)
      ))
    val ds: Dataset[Emp] = rdd.map(t => Emp(t._1, t._2, t._3)).toDS()
    ds.createOrReplaceTempView("emp")
//    spark.sql("select avgAge(age) from emp").show()
  val myAvgUDAFClass = new MyAvgUDAFClass //强类型注册比较麻烦
    ds.select(myAvgUDAFClass.toColumn).show()

    spark.stop()

  }


  class MyAvgUDAF extends UserDefinedAggregateFunction{
    override def inputSchema: StructType = {StructType(Array(StructField("age",LongType)))}

    override def bufferSchema: StructType = {StructType(Array(
      StructField("totalAge",LongType),
      StructField("count",LongType)
    ))}

    override def dataType: DataType = LongType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0)=0L
      buffer(1)=0L
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      buffer(0)=buffer.getLong(0)+input.getLong(0)
      buffer(1)=buffer.getLong(1)+1

    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0)=buffer1.getLong(0)+buffer2.getLong(0)
      buffer1(1)=buffer1.getLong(1)+buffer2.getLong(1)

    }

    override def evaluate(buffer: Row): Any = {
      buffer.getLong(0)/buffer.getLong(1)
    }
  }

  class MyAvgUDAFClass extends  Aggregator[Emp,AvgBuffer,Long]{
    override def zero: AvgBuffer = AvgBuffer(0L,0L)

    override def reduce(b: AvgBuffer, a: Emp): AvgBuffer = {
      b.totalAge=b.totalAge+a.age
      b.count=b.count+1
      b
    }

    override def merge(b1: AvgBuffer, b2: AvgBuffer): AvgBuffer = {
      b1.totalAge=b1.totalAge+b2.totalAge
      b1.count=b1.count+b2.count
      b1
    }

    override def finish(reduction: AvgBuffer): Long = reduction.totalAge/reduction.count

    override def bufferEncoder=Encoders.product

    override def outputEncoder=Encoders.scalaLong
  }

  case class Emp(id:Int,name:String,age:Long)
  case class  AvgBuffer(var totalAge:Long,var count:Long)
  case  class User(id:Int,name:String,age:Long)
}
