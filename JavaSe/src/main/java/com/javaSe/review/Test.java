package com.javaSe.review;

/**
 * @author ddone
 * @date 2020/7/21-09:02
 */
public class Test {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1,2,4,6,7,12,32,44,67,88,109,213,556};
        int target1 = 234;
//		target1 = 1009;
        int head = 0;//默认的首索引
        int end = arr1.length - 1;//默认的尾索引
        boolean isFlag1 = false;//判断是否找到了指定元素
        while(head <= end){
            int middle = (head + end) / 2;
            if(arr1[middle] == target1){
                System.out.println("找到了" + target1 + "元素，位置为：" + middle);
                isFlag1 = true;
                break;
            }else if(arr1[middle] > target1){
                end = middle - 1;
            }else{
                head = middle + 1;
            }

        }
        if(!isFlag1){
            System.out.println("没找到指定的元素");
        }
    }
}
