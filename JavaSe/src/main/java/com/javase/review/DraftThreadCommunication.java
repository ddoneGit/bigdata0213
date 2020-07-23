package com.javase.review;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ddone
 * @date 2020/7/23-09:41
 */
//使用两个线程交替打印
public class DraftThreadCommunication {
    public static void main(String[] args) {
        PrintTest p1 = new PrintTest();
        Thread t1 = new Thread(p1, "线程一");
        Thread t2 = new Thread(p1, "线程二");
        t1.start();
        t2.start();
    }
}

class PrintTest implements Runnable {
    private int number = 1;

    @Override
    public void run() {

        while (true) {
            synchronized (this) {
                notify();
                if (number <= 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" : "+number);
                    number++;
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}
