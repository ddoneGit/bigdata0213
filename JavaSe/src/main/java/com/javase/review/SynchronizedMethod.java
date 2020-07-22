package com.javase.review;

import javax.lang.model.element.VariableElement;

/**
 * @author ddone
 * @date 2020/7/22-22:54
 */
public class SynchronizedMethod {
    public static void main(String[] args) {
        Windows04 w = new Windows04();
        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);
        t2.start();
        t1.start();
        t3.start();
    }


}
class Windows04 implements Runnable{
    private int ticket = 100;

    @Override
    public void run() {
        show();
    }

    private synchronized void show() {
        while (true){
            if(ticket>0){
                try {
                    Thread.sleep(500);
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
