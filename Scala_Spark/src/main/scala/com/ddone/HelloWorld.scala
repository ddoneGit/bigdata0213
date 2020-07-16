package com.ddone

import scala.io.StdIn

/**
 *
 * *
 *
 * @author ddone
 *         *
 * @date 2020/7/15-20:44
 *
 */
object HelloWorld {
  def main(args: Array[String]): Unit = {
    //   println("HelloWorld")//①

    //    val i = 5
    //    var j = 7
    //    //    i=j 错误
    //    j = i
    //    printf("i=%d,j=%d",i,j)
    //    println(s"i=$i,j=$j")
    val num: Int = StdIn.readInt()
    println("输入的数字加上2后,结果是" + (num + 2))
  }

}
