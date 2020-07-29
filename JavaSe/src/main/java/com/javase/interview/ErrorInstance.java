package com.javase.interview;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ddone
 * @date 2020/7/21-23:52
 */
public class ErrorInstance {
    public static void main(String[] args) {
//        main(args);//StackOverflowError
//        byte[] arr = new byte[Integer.MAX_VALUE];//OutOfMemoryError
        Lock lock = new ReentrantLock();
        lock.lock();
        try {

        }finally {
            lock.unlock();
        }
    }
}
