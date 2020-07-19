package com.javaSe.review;

import java.util.Arrays;
import java.util.Random;

/**
 * @author ddone
 * @date 2020/7/19-23:14
 */
/*
题目十三 创建一个长度为6的int型数组，要求数组元素的值都在1-30之间，
且是随机赋值。同时，要求元素的值各不相同。  提示：Math.random()。

 */
public class RandomArray {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(randomArray(6)));

    }

    public static int[] randomArray(int num) {
        Random r = new Random();
        int[] resultArray = new int[num];
        for (int i = 0; i < resultArray.length; i++) {
            while (true) {
                int randNum = r.nextInt(30)+1 ;
                int count = 0;
                for (int j = 0; j < i; j++) {
                    if (resultArray[j] == randNum) {
                        count++;
                    }
                }
                if (count == 0) {
                    resultArray[i] = randNum;
                    System.out.println(resultArray[i]);
                    break;
                }

            }

        }
        return resultArray;
    }
}