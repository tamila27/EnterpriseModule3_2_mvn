package com.goit.gojavaonline.module3part2.theory;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;

/**
 * Created by tamila on 6/14/16.
 */
public class Synchronizers {
    public static void main(String[] args) throws InterruptedException {
        Synchronizers synchronizers = new Synchronizers();
        synchronizers.testCyclickBarier();
        //synchronizers.testExchanger();

    }
    public void testCyclickBarier() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(5);
        while(true){
            new Thread(() -> {
                try {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + " starts  waiting on barrier");
                    barrier.await();
                    System.out.println(threadName + " finish waiting");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(1000);
        }
    }

    public void testExchanger(){
        Exchanger<String> exchanger = new Exchanger<>();
        Random random = new Random();
        IntStream.range(0, 2).forEach((i) -> new Thread(() -> {
            try {
                Thread.sleep(random.nextInt(10000));
                System.out.println();
                String name = Thread.currentThread().getName();
                System.out.println(name + " ready to exchange");
                System.out.println(name + " < - > " + exchanger.exchange(name));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());

    }
}
