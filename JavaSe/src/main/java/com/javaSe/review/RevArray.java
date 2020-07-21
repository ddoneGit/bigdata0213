package com.javaSe.review;

import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/21-00:16
 */
public class RevArray {
    public static void main(String[] args) {
        int[] arr = {1, 5, 3, 0, 4};
        int[] brr = revArr(arr);
        System.out.println(Arrays.toString(brr));
    }

    private static int[] revArr(int[] arr) {
        for (int i = 0; i < arr.length/2; i++) {
            int temp=arr[i];
            arr[i] = arr[arr.length -1-i];
            arr[arr.length-1- i]=temp;
        }
        return arr;
    }


}
