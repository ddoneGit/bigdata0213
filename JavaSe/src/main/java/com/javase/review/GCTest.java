package com.javase.review;

import java.util.Random;

/**
 * @author ddone
 * @date 2020/7/27-13:32
 */
public class GCTest {
    public static void main(String[] args) {

        getSystemInfo();
        oomAndGcTest();
    }

    private static void oomAndGcTest() {
        String str = "测试";
        while (true){
            str+=str+new Random().nextInt(888888888)+new Random().nextInt(99999999);
        }
    }

    private static void getSystemInfo() {
        System.out.println("MaxMemory:"+Runtime.getRuntime().maxMemory()/1024/1024+": MB");//返回Java虚拟机的最大内存量
        System.out.println("TotalMemory:"+Runtime.getRuntime().totalMemory()/1024/1024+":MB");//虚拟机的内存总量
        System.out.println("核心数 :"+Runtime.getRuntime().availableProcessors());//返回CPU数
    }
}
