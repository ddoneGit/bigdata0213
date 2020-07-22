package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/19-21:54
 */
public class DiffMaxAndMin {
    public static void main(String[] args) {
        int[] arr = {1, -9, 2, 5, -3, 7};
        System.out.println(diffMaxAndMin(arr));

    }

    public static int diffMaxAndMin(int[] arr){
        int max = arr[0];
        int min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if(max<arr[i]){
                max=arr[i];
            }

            if(min>arr[i]){
                min = arr[i];
            }
        }

        return max-min;
    }
}
