package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-21:09
 */
public class RunnableTest03 {
    public static void main(String[] args) {
        Windows w = new Windows();
        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);
        t1.start();
        t2.start();
        t3.start();


    }
}
class Windows implements Runnable{
    private int ticket=100;
    @Override
    public void run() {
        //ticket需要变化,用循环
        while (true){
            synchronized (this) {
                if(ticket>0){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":"+ticket);
                    ticket--;
                }else {
                    break;
                }
            }
        }
    }
}
