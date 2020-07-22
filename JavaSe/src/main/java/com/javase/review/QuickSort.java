package com.javase.review;

import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/21-00:52
 */
/*
1.递归
2.while死循环  1个死循环,两个没有循环体的while循环
3.head end
4.high low
5.两个if,三个while,两个swap,两次调用
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {3, 5, 7, 2, 4};
        int[] qsArr = quickSort(arr);
        System.out.println(Arrays.toString(qsArr));
    }

    private static int[] quickSort(int[] arr) {
        subSort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void subSort(int[] arr, int start, int end) {
        if (start < end) {//没排完 ①
            int base = arr[start];//第一个元素 ②
            int low = start;
            int high = end+1;//③???
            while (true){
                while (low<end&&arr[++low]-base <= 0);
                while (high>start&&arr[--high]-base>=0);

                if(low<high){
                   swap(arr,low,high);
                }else {
                    break;
                }
            }//④while循环做完
            swap(arr,start,high);
            subSort(arr,start,high-1);
            subSort(arr,high+1,end);
        }

    }

    public static void swap(int[] data,int i ,int j){
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }


}
