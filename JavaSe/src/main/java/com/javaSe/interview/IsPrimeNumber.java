package com.javaSe.interview;

/**
 * @author ddone
 * @date 2020/7/14-23:11
 */
public class IsPrimeNumber {
    public static void main(String[] args) {
        System.out.println(isPrimNumber(17));

    }
    public static boolean isPrimNumber(int num){
        for (int i=2 ; i<=Math.sqrt(num);i++){
            if(num % i ==0){
                System.out.println(i);
               return false;
            }
        }
        return true;

    }
}
