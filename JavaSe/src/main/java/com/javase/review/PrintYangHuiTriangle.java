package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/15-08:18
 */

public class PrintYangHuiTriangle {
    public static void main(String[] args) {
        printYangHuiTriangle(20);
    }
    public static void printYangHuiTriangle(int num){
        int[][] array = new int[num][];
        for (int i = 0; i < array.length; i++) {
            array[i] = new int[i + 1];//初始化
            array[i][0]=array[i][i]=1;
            if(i>1){
               for (int j =1 ; j<array[i].length-1;j++){ //从第三行开始
                   array[i][j] = array[i - 1][j] + array[i - 1][j-1];
               }
            }
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]+"\t");
            }
            System.out.println();
        }
    }
    /**
     * 总结:
     *      ①需要对数组进行赋值,然后遍历
     *      ②行的长度等于行号+1
     *      ③第一个和最后一个值为1
     *      ④从第三行开始, j<array[i].length-1
     *
     *      ⑤遍历时必须换行
     *
     */
}
