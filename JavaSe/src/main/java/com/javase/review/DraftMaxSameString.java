package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/23-15:09
 */
public class DraftMaxSameString {
    public static void main(String[] args) {
        String str1 = "abcerthelloyuiodef";
        String str2 = "abcerhhhxthell";
        String maxSameString = findMaxSameString(str1, str2);
        System.out.println(maxSameString);
    }

    private static String findMaxSameString(String str1, String str2) {
        if(str1!=null&&str2!=null){
            String maxStr = str1.length() >=str2.length() ? str1 : str2;
            String minStr = str1.length() < str2.length() ? str1 : str2;
            for (int i = 0; i < minStr.length(); i++) {
                for (int x = 0,y=minStr.length()-i; y <=minStr.length(); x++,y++) {
                    if(maxStr.contains(minStr.substring(x,y))){
                        return minStr.substring(x, y);
                    }
                }

            }
        }

        return null;
    }
}
