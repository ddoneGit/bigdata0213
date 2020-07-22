package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-21:23
 */
public class ThreadSafe {
    public static void main(String[] args) {
        Windows2 w1 = new Windows2();
        Windows2 w2 = new Windows2();
        Windows2 w3 = new Windows2();
        w1.start();
        w2.start();
        w3.start();
    }
}

class Windows2 extends Thread{
    private static int ticked=100;

    @Override
    public void run() {
        while (true){
            synchronized (Windows2.class) {
                if(ticked>0){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" : "+ticked);
                    ticked--;
                }else {
                    break;
                }
            }
        }
    }
}
