package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-20:34
 */
//创建两个分线程，其中一个线程遍历100以内的偶数，另一个线程遍历100以内的奇数。
public class RunnableTest02 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if(i % 2!=0){
                        System.out.println(Thread.currentThread().getName()+" : "+i);
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if(i % 2==0){
                        System.out.println(Thread.currentThread().getName()+":"+i);
                    }
                }
            }
        }).start();
    }
}
