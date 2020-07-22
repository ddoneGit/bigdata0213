package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/15-00:22
 */
public class PrintRevTriangle {
    public static void main(String[] args) {
            printRevTriangle(6);
    }

    public static void printRevTriangle(int num) {
        for (int i = 0; i < num; i++){
            for (int j = 0 ;j<=i;j++){
                System.out.print(" ");
            }
            for (int k = 0 ;k<num-i;k++){
                System.out.print("* ");
            }
            System.out.println();
        }

    }

}
