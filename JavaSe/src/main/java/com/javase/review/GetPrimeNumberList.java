package com.javase.review;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ddone
 * @date 2020/7/23-17:06
 */
/*
•	随机生成30个数，范围2-100，获取其中的质数。
•	代码实现，效果如图所示：
 */
public class GetPrimeNumberList {
    public static void main(String[] args) {
        int size=30;
        int start=2;
        int end=100;
        List numList= generateNumberList(size,start,end);
        List primeNumberList=getPrimeNumberFromList(numList);
        System.out.println("随机数为: ");
        System.out.println(numList);
        System.out.println("其中质数为: ");
        System.out.println(primeNumberList);
    }

    private static List getPrimeNumberFromList(List numList) {
        ArrayList<Integer> primeList = new ArrayList<>();
        l:for (int i = 0; i < numList.size(); i++) {
            int number= (int) numList.get(i);
            for (int j = 2; j <= Math.sqrt(number); j++) {
                if(number%j==0){
                   continue l ;
                }
            }
            primeList.add(number);

        }
        return primeList;
    }

    private static List generateNumberList(int size, int start, int end) {
        ArrayList<Integer> resultList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            resultList.add(random.nextInt(end-start)+start+1);
        }
        return resultList;
    }
}
