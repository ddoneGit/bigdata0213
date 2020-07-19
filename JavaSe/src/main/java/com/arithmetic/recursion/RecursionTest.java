package com.arithmetic.recursion;

/**
 * @author ddone
 * @date 2020/7/19-19:32
 */
public class RecursionTest {
    public static void main(String[] args) {
        int num = 50000;
        System.out.println(tailRecSum(num,0));
//        System.out.println(recSum(num));
    }

    public static Integer recSum(int num){
        if(num==1){
           return 1;
        }else {
            return recSum(num - 1)+num;
        }
    }
    public static Integer tailRecSum(int num,int sum){
        if(num ==0){
            return sum ;
        }else {
            return tailRecSum(num - 1, sum+num);
        }
    }
}
