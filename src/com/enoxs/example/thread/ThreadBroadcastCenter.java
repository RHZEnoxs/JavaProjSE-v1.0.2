package com.enoxs.example.thread;

import java.util.Scanner;

public class ThreadBroadcastCenter extends Thread{
    /**
     * Thread Broadcast Center
     */
    Thread currentThread;
    static StringBuffer sb = new StringBuffer(1024);
    @Override
    public void run(){
        while(true){
            try {
                currentThread.sleep(5000);
                System.out.println(sb.toString());
                sb.setLength(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void post(String msg){
        sb.append(msg + "\n");
    }

    public ThreadBroadcastCenter(){
        currentThread = Thread.currentThread();
//        currentThread.run();
    }

    /**
     * Main Thread
     */
    public static void main(String args[]){
        ThreadBroadcastCenter center = new ThreadBroadcastCenter();
        center.start();

        ClientWorker c1 = new ClientWorker("C1");
        c1.setBroadcastCenter(center);
        c1.start();

        ClientWorker c2 = new ClientWorker("C2");
        c2.setBroadcastCenter(center);
        c2.start();

        ClientWorker c3 = new ClientWorker("C3");
        c3.setBroadcastCenter(center);
        c3.start();


        Scanner input = new Scanner(System.in);
        String ctrl;
        while(true){
            ctrl = input.next();
            switch (ctrl){
                case "1":
                    System.out.println("Hello");
                    break;
                case "2":
                    c3.postSomethingNew("HaHa");
                    break;
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }
    /**
     * Thread
     */
    public static class ClientWorker extends Thread{
        int count = 0;
        String tag;
        Thread currentThread;
        ThreadBroadcastCenter center;
        public ClientWorker(String tag){
            this.tag = tag;
        }
        public void setBroadcastCenter(ThreadBroadcastCenter center){
            this.center = center;
        }
        @Override
        public void run(){
            this.currentThread = Thread.currentThread();
            while(true){
                try {
                    currentThread.sleep(2000);
                    count ++;
                    center.post(tag + "#" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void postSomethingNew(String msg){
            center.post(tag + "SomethingNew#" + count + " - " + msg);
        }
    }

}
