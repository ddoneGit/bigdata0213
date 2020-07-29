package com.javase.review;


/**
 * @author ddone
 * @date 2020/7/26-16:55
 */
/*
main方法只是一个入口,操作资源类
 */
public class ThreadDraft {
    public static void main(String[] args) {
//        printNumber();
        testComsumerAndProducer();
    }

    private static void testComsumerAndProducer() {
        ClerkP c = new ClerkP();
        new Thread(()->{
            while (true){
                c.incProduct();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "生产者").start();
        new Thread(() -> {
            while (true){
                c.decProduct();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();


        new Thread(() -> {
            while (true){
                c.decProduct();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者二").start();

    }
    private static void printNumber() {
        PrintNumber pn = new PrintNumber();
        Runnable r = () -> {
            pn.printNum();
        };
        Thread t1 = new Thread(r, "甲");
        Thread t2 = new Thread(r, "乙");
        t1.start();
        t2.start();

    }
}

class PrintNumber {
    private int num;

    public synchronized void printNum() {
        while (true) {
            notifyAll();
            if (num < 100) {
                num++;
                System.out.println(Thread.currentThread().getName() + ":" + num);
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

class ClerkP {
    private int pAmount;//产品数量

    public synchronized void incProduct() {
//        notifyAll();
//        if (pAmount < 20) {
//            System.out.println(Thread.currentThread().getName() + "生产了第 " + ++pAmount + " 个产品");
//        }else {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        while (pAmount>=20){//防止虚假唤醒
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pAmount++;
        System.out.println(Thread.currentThread().getName() + "生产了第 " + pAmount + " 个产品");
        notifyAll();
    }
    public synchronized void decProduct() {
//        notify();
//        if (pAmount > 0) {
//            System.out.println(Thread.currentThread().getName() + "消费了第 " + pAmount-- + " 个产品");
////            pAmount--;
//        }else {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        while (pAmount<=0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "消费了第 " + (pAmount) + " 个产品");
        pAmount--;
        notifyAll();
    }

}