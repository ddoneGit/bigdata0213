package com.javase.stringtest;

import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/21-21:01
 */
public class Test {
    public static void main(String[] args) {
        char[] arr = {'a', 'l', 'f', 'm', 'f', 'b', 'b', 'b', 's', 'n'};
        Arrays.sort(arr);

        int count = 1;//默认的每个字符出现的次数
        for (int i = 0; i < arr.length; i++) { //时间复杂度O(n)

            if (i == arr.length - 1) {
                System.out.println(arr[i] + "----->" + count);
                break;
            }

            if (arr[i] == arr[i + 1]) {
                count++;
            } else {
                System.out.println(arr[i] + "----->" + count);
                count = 1;
            }

        }
    }
}