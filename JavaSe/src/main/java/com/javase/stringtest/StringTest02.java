package com.javase.stringtest;

import java.util.Arrays;

/**
 * @author ddone
 * @date 2020/7/21-21:04
 */
public class StringTest02 {
    public static void main(String[] args) {
        char[] charArray = {'a', 'l', 'f', 'm', 'f', 'b', 'b', 'b', 's', 'n'};
        Arrays.sort(charArray);
        countChars(charArray);
    }

    private static void countChars(char[] charArray) {
        int count=1;
        for (int i=0;i<charArray.length;i++){
            if(i==charArray.length-1){
                System.out.print(charArray[i]+"-->"+count+" ");
                break;
            }

            if(charArray[i]==charArray[i+1]){
               count++;
            }else {
                System.out.print(charArray[i]+"-->"+count+" ");
                count=1;
            }
        }
    }
}
