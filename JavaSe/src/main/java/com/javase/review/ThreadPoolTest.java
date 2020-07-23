package com.javase.review;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ddone
 * @date 2020/7/23-11:19
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        //1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor service = (ThreadPoolExecutor) executorService;
        service.setMaximumPoolSize(20);
        service.execute(new NumTest());
        service.execute(new NumTest1());
        service.shutdown();
    }
}
class NumTest implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2==0){
                System.out.println(Thread.currentThread().getName()+" : "+i);
            }
        }
    }
}
class NumTest1 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2!=0){
                System.out.println(Thread.currentThread().getName()+" : "+i);
            }
        }
    }
}
