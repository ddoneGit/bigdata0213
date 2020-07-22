package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-00:57
 */
//开启三个窗口售票，总票数为100张。
public class ThreadTest03 {
    public static void main(String[] args) {
        Window w1 = new Window();
        Window w2 = new Window();
        Window w3 = new Window();
        w1.start();
        w2.start();
        w3.start();
    }
}

class Window extends Thread{
    private static Integer tickets=100;

    @Override
    public void run() {
        while (true){
            if(tickets>0){
                System.out.println(Thread.currentThread().getName()+":"+tickets);
                tickets--;
            }else {
                break;
            }
        }
    }
}
