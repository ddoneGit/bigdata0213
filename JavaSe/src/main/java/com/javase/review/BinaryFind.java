package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/21-00:27
 */
//二分查找必须是有序的,查找高于线性查找
public class BinaryFind {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
//        Arrays.sort(arr);
        binaryFind(arr, 9);
//        System.out.println(Arrays.toString(arr));

    }

    private static void binaryFind(int[] arr, int target) {
        int head = 0;
        int end = arr.length - 1;
        boolean flag = false;
        while (head <= end) {
            int mid = (head + end) / 2;
            if (arr[mid] == target) {
                System.out.println("找到了,索引为:" + mid);
                flag = true;
                break;
            } else if (arr[mid] > target) {
                end = mid - 1;
            } else {
                head = mid+ 1;
            }
        }
        if (!flag) {
            System.out.println("没找到");
        }


    }
}

















