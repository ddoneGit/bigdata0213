package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/14-20:37
 */

import java.util.Scanner;

/**
 * 练习 scanner和switch
 */
public class Switch_Scan {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        char c = str.charAt(0);
        switch (c) {
            case 'a':
                System.out.println("the letter is a");
                break;
            case 'b':
                System.out.println("b");
                break;
            case 'c':
                System.out.println("c");
                break;
            case 'd':
                System.out.println("d");
                break;
            default:
                System.out.println("other");
        }

    }
}

