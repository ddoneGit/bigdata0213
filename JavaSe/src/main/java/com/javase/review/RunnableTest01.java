package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-20:21
 */
public class RunnableTest01 {
    public static void main(String[] args) {
        new Thread(new PrintNum()).start();
    }
}

class PrintNum implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
//            创建分线程遍历100以内的偶数
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+i);
            }
        }
    }
}
