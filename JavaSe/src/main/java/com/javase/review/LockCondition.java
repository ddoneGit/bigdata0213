package com.javase.review;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ddone
 * @date 2020/7/26-22:36
 */
/*
多线程之间调用,实现A->B->C
 */
public class LockCondition {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print5();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print10();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print15();
            }
        }, "CC").start();

    }
}

class ShareData  {
    private int number=1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    private int m1 = 1;
    private int m2 = 2;
    private int m3 = 3;

    public void print(Lock lock, Condition con1,Condition con2,int mark,int nextMark,int times) {
        lock.lock();
        try {//1.判断
            while (number != mark){
                con1.await();
            }
            //2.干活
            for (int i = 1; i <=times; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3.通知
            number=nextMark;
            con2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print5(){
        print(lock,c1,c2,m1,m2,5);
    }
    public void print10(){
        print(lock,c2,c3,m2,m3,10);
    }
    public void print15(){
        print(lock,c3,c1,m3,m1,15);
    }
}
