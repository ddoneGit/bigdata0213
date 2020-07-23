package com.javase.review;


/**
 * @author ddone
 * @date 2020/7/23-13:48
 */
//将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反转为”abfedcg”
public class SubStringCount {
    public static void main(String[] args) {
      //获取“ab”在 “abkkcadkabkebfkabkskab” 中出现的次数
        String str = "abkkcadkabkebfkabkskab";
        String subStr = "ab";
        int count1 = findCountSubStr1(str, subStr);
        int count2 = findCountSubStr2(str, subStr);
        System.out.println(count2);
    }

    private static int findCountSubStr2(String str, String subStr) {
        int count=0;
        int index=0;
        //索引+截取
        while ((index=str.indexOf(subStr)) != -1){
            count++;
            str=str.substring(index+subStr.length());//索引+子串的长度
        }
        return count;
    }

    private static int findCountSubStr1(String str, String subStr) {
        //包含+替换
        int count=0;
       while (true){
           if(str.contains(subStr)){
              count++;
              str=str.replaceFirst(subStr,"");
           }else {
               break;
           }
       }
        return count;
    }

}
