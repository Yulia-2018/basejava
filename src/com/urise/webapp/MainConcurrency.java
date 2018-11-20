package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter = 0;

    public static final int THREAD_NUMBER = 10000;
    private static final MainConcurrency mainConcurrency1 = new MainConcurrency();
    private static final MainConcurrency mainConcurrency2 = new MainConcurrency();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
        final Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " " + getState());
                throw new RuntimeException();
            }
        };
        thread0.start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());

        MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            final Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc0();
                }
            });
            thread.start();
            threads.add(thread);
        }
        //Thread.sleep(500);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);

        // deadlock
        counter = 0;
        final Thread thread1 = new Thread(mainConcurrency1::inc);
        thread1.start();
        final Thread thread2 = new Thread(mainConcurrency2::dec);
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter);
    }

    private synchronized void inc0() {
        counter++;
    }

    private synchronized void inc() {
        counter++;
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mainConcurrency2.dec();
    }

    private synchronized void dec() {
        counter--;
        synchronized (mainConcurrency1) {
            mainConcurrency1.notify();
        }
        mainConcurrency1.inc();
    }
}
