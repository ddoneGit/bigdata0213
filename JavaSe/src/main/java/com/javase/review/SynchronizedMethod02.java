package com.javase.review;

/**
 * @author ddone
 * @date 2020/7/22-23:11
 */
public class SynchronizedMethod02 {
    public static void main(String[] args) {
        Account account = new Account();
        Customer c1 = new Customer("甲",account);
        Customer c2 = new Customer("乙",account);
        c1.start();
        c2.start();
    }

}
class Customer extends Thread{
    private Account account;

    public Customer(String name,Account account) {
        super(name);
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(5000);
                account.deposit(1000.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Account {
    private Double balance = 0.0;

    public  synchronized void deposit(Double amount) throws Exception {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName()+":"+balance);
        } else {
            throw new Exception("存款不能是负数");
        }

    }
}
//不加锁 1000.0
//1000.0
//2000.0
//3000.0
//4000.0
//4000.0
// 加锁
//甲:1000.0
//乙:2000.0
//甲:3000.0
//乙:4000.0
//甲:5000.0
//乙:6000.0
