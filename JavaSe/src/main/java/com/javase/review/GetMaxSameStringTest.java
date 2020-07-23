package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/23-14:29
 */
/**
 *
 * //获取两个字符串中最大相同子串。比如：
 * //	   str1 = "abcwerthelloyuiodef"; str2 = "cvhellobnm"
 * //	      提示：将短的那个串进行长度依次递减的子串与较长的串比较。
 */
public class GetMaxSameStringTest {
    public static void main(String[] args) {
//        String str1 = "avheerthellobyuiodef";
//        String str2 = "cvhellobnm";
        String str1="abcefgdgkk";
        String str2 ="abcxxxx";
        String maxSameString = getMaxSubString(str1,str2);
        System.out.println(maxSameString);

    }

    private static String getMaxSubString(String str1, String str2) {
        String maxStr = str1.length() > str2.length() ? str1 : str2;
        String minStr = str1.length() < str2.length() ? str1 : str2;
        for (int i = 0; i < minStr.length(); i++) {//从左往右
            for (int x = 0,y=minStr.length()-i; y <= minStr.length(); x++,y++) {//从右往左
                if(maxStr.contains(minStr.substring(x,y))){
                    return minStr.substring(x, y);
                }
            }

        }
        return null;
    }
}
