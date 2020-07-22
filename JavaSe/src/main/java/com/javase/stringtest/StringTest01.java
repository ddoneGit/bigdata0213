package com.javase.stringtest;

import java.util.*;

/**
 * @author ddone
 * @date 2020/7/21-20:45
 */
public class StringTest01 {
    public static void main(String[] args) {
        char[] charArray = {'a', 'l', 'f', 'm', 'f', 'b', 'b', 'b', 's', 'n'};
        countChars(charArray);
    }

    private static void countChars(char[] arr) {
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        for (char c : arr) {
            if(map.containsKey(c)){
                map.put(c, map.get(c) + 1);
            }else {
                map.put(c, 1);
            }
        }
        System.out.println(map);

    }



}
