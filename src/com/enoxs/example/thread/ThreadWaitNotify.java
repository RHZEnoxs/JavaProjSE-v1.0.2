package com.enoxs.example.thread;

public class ThreadWaitNotify {

    public ThreadWaitNotify(){
        Message msg = new Message("QwQ");
        Waiter waiter = new Waiter(msg);
        new Thread(waiter,"waiter#1").start();

        Waiter waiter1 = new Waiter(msg);
        new Thread(waiter1, "waiter#2").start();

        Notifier notifier = new Notifier(msg);
        new Thread(notifier, "notifier").start();
        System.out.println("All the threads are started");

    }
    public class Waiter implements Runnable {
        private Message msg;
        public Waiter(Message m) {
            this.msg = m;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            synchronized (msg) {
                try {
                    System.out.println(name + " waiting to get notified at time:" + System.currentTimeMillis());
                    msg.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " waiter thread got notified at time:" + System.currentTimeMillis());
                //process the message now
                System.out.println(name + " processed: " + msg.getMsg());
            }
        }
    }

    public class Notifier implements Runnable {
        private Message msg;
        public Notifier(Message msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + " started");
            try {
                Thread.sleep(1000);
                synchronized (msg) {
                    msg.setMsg(name + " Notifier work done");
//                    msg.notify();
                     msg.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public class Message {
        private String msg;
        public Message(String str){
            this.msg=str;
        }
        public String getMsg() {
            return msg;
        }
        public void setMsg(String str) {
            this.msg=str;
        }
    }
    public static void main(String args[]){
        new ThreadWaitNotify();
    }
}
