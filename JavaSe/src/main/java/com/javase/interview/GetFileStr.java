package com.javase.interview;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

/**
 * @author ddone
 * @date 2020/7/25-20:04
 */
/*
题目五： (20分)  写一个方法，输入一个文件名和一个字符串，统计这个字符串在这个文件中出现的次数
 */

public class GetFileStr {
    public static void main(String[] args) {
        String path = "input/word.txt";
        String str = "Hadoop";
        int strCount = getStrCountOfFile(path,str);
        System.out.println(strCount);
    }

    private static int getStrCountOfFile(String path, String str) {
        if(str == null){
            throw new RuntimeException("字符串不能为空");
        }
        int count= 0;
        BufferedReader br=null;
        try {
             br = new BufferedReader(new FileReader(path));
            String data;
            while ((data=br.readLine())!=null){
                String[] splits = data.split(" ");//readLine方法,每次读取一行数据
                for (int i = 0; i < splits.length; i++) {
                    if(str.equals(splits[i])){
                       count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
