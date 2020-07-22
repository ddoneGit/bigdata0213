package com.javase.review;

import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/21-00:44
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {3, 5, 7, 2, 4};
        int[] sortArr = bubSort(arr);

        System.out.println(Arrays.toString(sortArr));
    }
//冒牌排序,两遍for循环//边界条件是length-1
    private static int[] bubSort(int[] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-1; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
        }


        return arr;
    }
}
