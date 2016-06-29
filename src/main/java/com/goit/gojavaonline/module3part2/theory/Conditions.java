package com.goit.gojavaonline.module3part2.theory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Created by tamila on 6/15/16.
 */
public class Conditions {

    public static void main(String[] args) {
        TransferQueue<String> transferQueue = new TransferQueue(10);
        IntStream.range(0,5).forEach((i) -> new Thread(() -> {
            while (true){
                try{
                    transferQueue.put(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());

        new Thread(() -> {
            while(true){
                try{
                    System.out.println("Taking out: " + transferQueue.take());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static class TransferQueue<T> {
        private final Lock lock = new ReentrantLock();
        private final Condition full = lock.newCondition();
        private final Condition empty = lock.newCondition();

        Object[] items;
        int putIndex;
        int takeIndex;
        int size;

        public TransferQueue(int capacity) {
            items = new Object[capacity];

        }

        public void put(T item) throws InterruptedException {
            lock.lock();
            try{
                if(size == items.length){
                    full.await();
                    System.out.println("Queue is full: " + Thread.currentThread().getName() + " start waiting.....");
                }
                System.out.println(Thread.currentThread().getName() + " is putting");
                items[putIndex] = item;
                if (++putIndex == items.length) {
                    putIndex = 0;
                }
                size++;
                empty.signal();
            } finally {
                lock.unlock();
            }

        }

        public T take() throws InterruptedException {
            lock.lock();
            try {
                if(size == 0) {
                    empty.await();
                }
                T result = (T) items[takeIndex];
                if (++takeIndex == items.length) {
                    takeIndex = 0;
                }
                size--;
                full.signal();
                return result;
            } finally {
                lock.unlock();
            }
        }
    }

}
