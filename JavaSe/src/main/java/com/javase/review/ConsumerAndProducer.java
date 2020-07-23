package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/23-10:19
 */

// * 例题：生产者、消费者问题：
//         * 生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，
//         * 店员一次只能持有固定数量的产品(比如:20），如果生产者试图生产更多的产品，店员会叫生产者停一下，
//         * 如果店中有空位放产品了再通知生产者继续生产；如果店中没有产品了，店员会告诉消费者等一下，如果店中
//         * 有产品了再通知消费者来取走产品。
public class ConsumerAndProducer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer p = new Producer(clerk, "生产者");
        Consumer c1 = new Consumer(clerk, "消费者");
        Consumer c2 = new Consumer(clerk, "消费者");
        Consumer c3 = new Consumer(clerk, "消费者");
        Consumer c4 = new Consumer(clerk, "消费者");
        Consumer c5 = new Consumer(clerk, "消费者");
        p.start();
        c1.start();
        c2.start();
        c3.start();

    }

}
class Producer extends  Thread{
    //线程一
    private Clerk clerk;

    public Producer(Clerk clerk,String name) {
        super(name);
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("开始生成产品");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.addProduct();
        }
    }
}
class Consumer extends  Thread{
    private Clerk clerk;

    public Consumer(Clerk clerk,String name) {
        super(name);
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println("开始消耗产品");
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.subProduct();
        }
    }
    //线程二
}
class Clerk{
    private int amount;
    //资源类
    public synchronized void addProduct(){
        if(amount<20){
           amount++;
            System.out.println(Thread.currentThread().getName()+"生成了第"+amount+"个产品");
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
       if(amount>0){
          amount--;
           System.out.println(Thread.currentThread().getName()+"消费了第"+amount+"个产品");
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
