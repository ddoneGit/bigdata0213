package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-00:45
 */
public class ThreadTest01 {
    public static void main(String[] args) {
        EventNumber eventNumber = new EventNumber();
        eventNumber.start();
        eventNumber.start();//IllegalThreadStateException

    }
}

class EventNumber  extends Thread{
    @Override
    public void run() {
        for (int i = 0; i <=100; i++) {
            if(i%2==0){
                System.out.println(i);
            }
        }
    }
}
