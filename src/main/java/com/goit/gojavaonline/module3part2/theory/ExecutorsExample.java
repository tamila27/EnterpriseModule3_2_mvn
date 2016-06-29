package com.goit.gojavaonline.module3part2.theory;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by tamila on 6/16/16.
 */
public class ExecutorsExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //new ExecutorsExample().testScheduledAtFixedRate();

        new ExecutorsExample().testShutdown();
    }

    public void testShutdown() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        IntStream.range(0, 5).forEach((i) -> executor.execute(() -> {
            long j = 0;
            while (true) {
                System.out.println(Thread.currentThread().getName() + " : " + (j++));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.yield();
            }
        }));
        executor.shutdownNow();
        System.out.println("=================");
        System.out.println("shutdown");
        System.out.println("=================");

    }

    public void testExecute() {
        Executor executor = Executors.newSingleThreadExecutor();
        System.out.println(Thread.currentThread().getName() + "submit task");
        try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //System.out.println(Thread.currentThread().getName() + "Async task started");
                    throw new RuntimeException("Exception occurred");
                }
            });
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testSubmit() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> f = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Task executed";
            }
        });
        System.out.println("Waiting for result");
        System.out.println("Result: " + f.get());
        executorService.shutdown();
    }

    public void testException() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> f = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new RuntimeException("Exception happened");
            }
        });
        System.out.println("Waiting for result");
        Thread.sleep(1000);
        try {
            System.out.println("Result: " + f.get());
        } catch (ExecutionException e) {
            System.out.println("Exception occurred");
        }

        executorService.shutdown();
    }

    public void testInvokeAny() throws ExecutionException, InterruptedException {
        List<Callable<String>> callables = new ArrayList<>();
        Random random = new Random();
        IntStream.range(0, 3).forEach(i -> callables.add(() -> {
            Thread.sleep(random.nextInt(1000));
            return String.valueOf(i);
        }));
        ExecutorService executor = Executors.newCachedThreadPool();
        String result = executor.invokeAny(callables);

        System.out.println(result);

        executor.shutdown();

    }

    public void testInvokeAll() throws ExecutionException, InterruptedException {
        List<Callable<String>> callables = new ArrayList<>();
        Random random = new Random();
        IntStream.range(0, 3).forEach(i -> callables.add(() -> {
            Thread.sleep(random.nextInt(1000));
            return String.valueOf(i);
        }));
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> result = executor.invokeAll(callables);

        for (Future f : result) {
            System.out.println(f.get());
        }
        executor.shutdown();

    }

    public void testScheduled() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("Task scheduled " + new Date());
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task executed " + new Date());
            }
        }, 1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    public void testScheduledAtFixedRate() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("Task scheduled " + new Date());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task executed " + new Date());
            }
        }, 1, 1, TimeUnit.SECONDS);
        Thread.sleep(10000);
        executorService.shutdown();
    }
}
