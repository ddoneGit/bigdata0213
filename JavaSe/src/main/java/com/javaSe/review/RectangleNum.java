package com.javaSe.review;

/**
 * @author ddone
 * @date 2020/7/19-23:51
 */
public class RectangleNum {
    public static void main(String[] args) {
        printRectangleNum(3);

    }

    public static void printRectangleNum(int num) {
        int[][] arr = new int[num][num];
        int count = 0;//要显示的数据
        int maxX = num - 1;//x轴的最大下表
        int maxY = num - 1;//y轴的最大小表
        int minX = 0; //x轴的最小下标
        int minY = 0;//y轴的最小下标
        while (minX <=maxX) {
            for (int x = minX;x<=maxX;x++){
                arr[minY][x] = ++count;
            }//赋值第一行
            minY++;
            for (int y =minY;y<=maxY;y++){
                arr[y][maxX] = ++count;
            }//赋值最后一列
            maxX--;
            for (int x=maxX;x>=minX;x--){
                arr[maxY][x] = ++count;
            }
            maxY--;
            for (int y = maxY;y>=minY;y--){
                arr[y][minX] = ++count;
            }
            minX++;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                String space= arr[i][j]<10? "0":"";
                System.out.print(space+arr[i][j]+" ");
            }
            System.out.println(" ");
        }

    }
}
