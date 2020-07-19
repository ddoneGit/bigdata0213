package com.ddone

/**
 **
@author ddone
 **
 * @date 2020/7/19-19:46
 *
 */
object RecursionTest {
  def main(args: Array[String]): Unit = {
    println(tailRecSum(10000000,0))
    println(recSum(10000))
  }

  def recSum(num:Int):Int={
    if (num ==1 ){
       1;
    }else{
      num+recSum(num-1)
    }
  }

  def tailRecSum(num:Int,sum:Int): Int ={
    if (num==0){
      sum
    }else{
      tailRecSum(num-1,sum+num)
    }
  }
}
