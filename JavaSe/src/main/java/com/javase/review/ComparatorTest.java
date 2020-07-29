package com.javase.review;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ddone
 * @date 2020/7/23-15:45
 */
//创建String类型的数组，按照字符串从大到小的顺序排序。
public class ComparatorTest {
    public static void main(String[] args) {
        String[] arr = new String[]{"BBc","DdddfD","GGaaa","J","MMkkkkk","AA"};
        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return -(o1.length()-o2.length());
            }
        });

        System.out.println(Arrays.toString(arr));

    }
}
