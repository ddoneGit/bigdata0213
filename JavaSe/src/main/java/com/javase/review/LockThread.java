package com.javase.review;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ddone
 * @date 2020/7/23-00:54
 */
public class LockThread {
    public static void main(String[] args) {
        Window06 w = new Window06();
        new Thread(w).start();
        new Thread(w).start();
    }
}

class Window06 implements Runnable{
    private ReentrantLock lock = new ReentrantLock();
    //1.创建ReentrantLock
    private int  ticket=100;
    @Override
    public void run() {
        while (true){
            try {
                lock.lock();//2.在操作对象时,调用lock方法
                if(ticket>0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":"+ticket);
                    ticket--;
                }else {
                    break;
                }
            } finally {
                lock.unlock();//3.一定要执行,用哪个try...catch进行包裹
            }

        }
    }
}