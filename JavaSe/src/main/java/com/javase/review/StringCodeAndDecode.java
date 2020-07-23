package com.javase.review;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/23-11:45
 */
public class StringCodeAndDecode {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "国";
        byte[] gbks = s.getBytes("utf-8");
        System.out.println(Arrays.toString(gbks));
        System.out.println(new String(gbks,"GBK"));
    }
}
