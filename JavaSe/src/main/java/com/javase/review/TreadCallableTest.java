package com.javase.review;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ddone
 * @date 2020/7/23-11:08
 */
//例题：遍历100以内的偶数，并计算所有偶数的和
public class TreadCallableTest {
    public static void main(String[] args) {
        NumThread numThread = new NumThread();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(numThread);
        new Thread(futureTask).start();
        //可以获取结果
        try {
            Integer result = futureTask.get();
            System.out.println(result); //获取到的是call的返回值
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
class NumThread implements Callable{
    @Override
    public Integer call() throws Exception {
        int sum=0;
        for (int i = 0; i < 100; i++) {
            if(i % 2==0){
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }
}
