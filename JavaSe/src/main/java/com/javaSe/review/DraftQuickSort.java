package com.javaSe.review;


import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/21-08:18
 */
public class DraftQuickSort {
    public static void main(String[] args) {
        int[] arr = {3, 6, -1, 2, 7, 10};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));

    }

    public static int[] quickSort(int[] arr) {
        subSort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void subSort(int[] arr, int start, int end) {
        if (start < end) { //可以排序,防止顺序颠倒
            int base = arr[start];
            int low = start;
            int high = end;
            while (true){
                while (low < end && arr[++low] <= base); //元素放左边
                while (high>start && arr[--high] >= base);//元素放右边
                if(low <= high){
                    swapArr(arr, low, high);
                }else {
                    break;
                }
            }

            swapArr(arr,start,high);
            subSort(arr,start,high-1);
            subSort(arr,high+1,end);

        }


    }

    private static void swapArr(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
