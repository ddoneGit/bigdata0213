package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/23-14:18
 */
public class ReverseString {
    public static void main(String[] args) {
        // 将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反转为”abfedcg”
        String sourceStr = "abcdefg";
        String destStr = reverseStr(sourceStr,2,5);
        System.out.println(destStr);
    }

    private static String reverseStr(String sourceStr, int start, int end) {
        if(sourceStr!=null){
            char[] chArr = sourceStr.toCharArray();
            for (int i = start,j=end; i < j; i++,j--) {
                char temp=chArr[i];
                chArr[i]=chArr[j];
                chArr[j]=temp;
            }
            return new String(chArr);
        }

        return null;
    }
}
