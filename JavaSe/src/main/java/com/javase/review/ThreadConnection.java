package com.javase.review;

import java.util.HashMap;

/**
 * @author ddone
 * @date 2020/7/23-09:22
 */
//使用两个线程打印 1-100。线程1, 线程2 交替打印
public class ThreadConnection {
    public static void main(String[] args) {
        PrNum p = new PrNum();
        Thread t1 = new Thread(p, "线程一");
        Thread t2 = new Thread(p, "线程二");
        t1.start();
        t2.start();
    }
}
class PrNum implements Runnable{
    private int number=1;
    @Override
    public void run() {
        while (true){
            synchronized (this) {
                this.notify();
                if(number<=100){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" : "+number);
                   number++;
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    break;
                }
            }
        }
    }
}