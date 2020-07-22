package com.javase.review;

import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/21-09:02
 */
public class QuickSortDraft {
    public static void main(String[] args) {
        int[] arr = {1, 5, 3, -4, 2, 7,20,-1,99};
        quickSortArr(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSortArr(int[] arr) {
        subSortArr(arr,0,arr.length-1);
    }

    private static void subSortArr(int[] arr, int start, int end) {
    if(start < end){
        int base = arr[start];
        int low = start;
        int high = end + 1;
        while (true){
            while (low < end && arr[++low]<base);
            while (high>start && arr[--high]>base);
            if(low<high){
                swapArr(arr, low, high);
            }else {
                break;
            }
        }
        swapArr(arr,start,high);
        subSortArr(arr,start,high-1);
        subSortArr(arr,high+1,end);
    }
    }

    private static void swapArr(int[] arr, int i , int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
