package com.ddone

/**
 *
 * *
 *
 * @author ddone
 *         *
 * @date 2020/7/15-21:16
 *
 */
object ForCycleTest {
  def main(args: Array[String]): Unit = {
    //    for (i <- 1 to 10 ){
    //      println(" i = " + i)
    //    }
    //    for (i <- 1 to 10 by 3){
    //      println(" i = " + i)
    //    }
    //    for (i <- 1 until  10 by 3){
    //      println(" i = " + i)
    //    }
    //    for (i <-  Range(1,10,3)){
    //
    //      println(" i = " + i)
    //    }
//    for (i <- Range(1, 10) if i % 3 != 0) {
//      println("i=" + i)
//    }
    val num =9
    for(i <- 1 to 2 * num by 2;j=(2*num-i)/2){
      println(" "*j+"*"*i)
    }

  }

}
