package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/23-10:45
 */
public class DraftConsumerAndProducer {
    public static void main(String[] args) {
        Cl clerk = new Cl();
        Pro p = new Pro(clerk);
        Cons c1 = new Cons(clerk);
        Cons c2 = new Cons(clerk);
        Cons c3 = new Cons(clerk);
        p.setName("生产者");
        c1.setName("消费者一");
        c2.setName("消费者二");
        c3.setName("消费者三");
        p.start();
        c1.start();
        c2.start();
        c3.start();

    }
}
class Pro extends Thread{
    private Cl clerk;

    public Pro(Cl clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println("生成者开始生成商品");
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.addProduct();
        }
    }
}
class Cons extends Thread{
    private Cl clerk;

    public Cons(Cl clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println("生成者开始消费产品");
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.subProduct();
        }
    }
}
class Cl {
    private int productAmount;
    public synchronized void addProduct(){
        if(productAmount<20){
            productAmount++;
            System.out.println(Thread.currentThread().getName()+"生成了第"+productAmount+"个产品");
            notifyAll();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void subProduct(){
        if(productAmount>0){
            productAmount--;
            System.out.println(Thread.currentThread().getName()+"消费了第"+productAmount+"个产品");
            notifyAll();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}