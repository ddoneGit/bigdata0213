package com.javaSe.review;

/**
 * @author ddone
 * @date 2020/7/14-20:51
 */
/*
求两个数的最小公约数,和最大公倍数
 */
public class Divisor_Multi {
    public static void main(String[] args) {
        int num1 = 3;
        int num2 = 4;
        System.out.println(maxDivisor(num1, num2));
        System.out.println(minMulti(num1, num2));
    }
    public static int maxDivisor(int num1,int num2){
        int minNum = num1 > num2 ? num2 : num1;
        for (int i = minNum; i >=1; i--) {
            if(num1 % i == 0 && num2 % i ==0){
               return i;
            }
        }
        return 1;
    }
    public static int minMulti(int num1,int num2){
        int maxNum = num1 > num2 ? num1 : num2;
        for(int i = maxNum;i<= num1 * num2;i++){
            if(i % num1 == 0 && i % num2 ==0){
                return i;
            }
        }
        return num1*num2;
    }
}
