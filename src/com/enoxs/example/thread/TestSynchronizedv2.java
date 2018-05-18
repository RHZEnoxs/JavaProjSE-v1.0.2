package com.enoxs.example.thread;

public class TestSynchronizedv2 implements Runnable{

    static volatile int i = 0;

    public static synchronized void increase() {
        i++;
    }

    @Override public void run() {
        for (int j = 0; j < 1000000; j++) {
            increase();
        }
    }
    public static void main(String args[]) throws InterruptedException {
        TestSynchronizedv2 lockRunnable = new TestSynchronizedv2();
        TestSynchronizedv2 testRunnable = new TestSynchronizedv2();
        Thread t1 = new Thread(lockRunnable);
        Thread t2 = new Thread(testRunnable);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("i : " + i);
    }
}
