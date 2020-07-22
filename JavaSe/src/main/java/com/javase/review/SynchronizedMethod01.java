package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-23:01
 */
public class SynchronizedMethod01 {
    public static void main(String[] args) {
        Window05 w1 = new Window05();
        Window05 w2 = new Window05();
        Window05 w3 = new Window05();
        w1.start();
        w2.start();
        w3.start();
    }
}
class Window05 extends Thread{
    private static int tickets =100;

    @Override
    public void run() {
        show();

    }

    private synchronized static void show() {
        while (true){
            if(tickets>0){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" : "+tickets);
                tickets--;
            }else {
                break;
            }
        }
    }


}
