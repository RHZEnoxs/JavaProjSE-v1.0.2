package com.enoxs.example.thread;

public class TestSynchronizedv1 extends Thread{
    Object lock;

    TestSynchronizedv1(Object lock) {
        this.lock = lock;
    }

    static volatile int i = 0;

    public static void increase() {
        i++;
    }
    @Override
    public void run() {
        synchronized (lock) {
            for (int j = 0; j < 1000000; j++) {
                increase();
            }
        }
    }
    public static void main(String args[]) throws InterruptedException {
        Object lock = new Object();
        Object test = new Object();
        Thread t1 = new TestSynchronizedv1(lock);
        Thread t2 = new TestSynchronizedv1(lock);//test
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("i : " + i);
    }
}
