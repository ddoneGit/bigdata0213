package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/14-21:28
 */
public class PrimeNumber {
    public static void main(String[] args) {
        printPrimNumber(100);

    }
    public static void printPrimNumber(int num){
        l:for (int i = 2 ;i<= num;i++){
            for (int j=2;j<=Math.sqrt(i);j++){
                if(i % j == 0){
                   continue l;
                }
            }
            System.out.println(i+" ");
        }
    }
}
