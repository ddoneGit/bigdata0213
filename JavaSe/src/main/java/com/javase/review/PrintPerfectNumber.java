package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/15-00:02
 */
/*
一个数如果恰好等于它的因子之和，这个数就称为"完数"。
例如6=1＋2＋3。编程 找出1000以内的所有完数。（因子：除去这个数本身的其它约数）
 */
public class PrintPerfectNumber {
    public static void main(String[] args) {
//            printPerfectNumber(100);
        isPerfectNumber(28);
    }

    public static void printPerfectNumber(int num) {
        for (int i = 1; i <= num; i++) {
            isPerfectNumber(i);
        }

    }

    public static void isPerfectNumber(int i) {
        int sum=0;
        for (int j = 1; j < i; j++) {
            if(i % j == 0 ){
               sum+=j;
            }
        }
        if(sum==i){
            System.out.println(i+" ");
        }
    }
}
