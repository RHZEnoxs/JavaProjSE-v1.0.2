package com.enoxs.example.demo;


import java.util.ArrayList;
import java.util.List;

public class CallbackDemo {
    public static void main(String args[]){
        new CallbackDemo();
    }
    public CallbackDemo(){
        runTask1();
//        runTask2();
    }

    /**
     * btn.setOnClickListener(new Button.OnClickListener(){
     *      @Override
     *      public void onClick(View v) {
     *
     *      }
     *});
     */
    public void runTask1() {
        EventCenter eventCenter = new EventCenter();
        eventCenter.setOnEventListener(new OnEventListener() {
            @Override
            public void onEvent() {
                // If you have message. Tell me
                System.out.println("you get a message.");
            }
        });

        eventCenter.start();

    }


    class EventCenter extends Thread{
        Thread currentThread;
        int count;

        OnEventListener onEventListener;

        public EventCenter(){
            currentThread = Thread.currentThread();
            count = 0;
        }

        @Override
        public void run(){
            while(count < 1000){
                if((count+1) % 5 == 0){
                    trigger();
                }
                count ++;
                System.out.println("count #" + count);
                try {
                    currentThread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setOnEventListener(OnEventListener onEventListener) {
            // some operations
            this.onEventListener = onEventListener;
            // some operations
        }

        public void trigger(){
            onEventListener.onEvent();
        }
    }
    interface OnEventListener {
        void onEvent();
    }


    List<Listener> listeners = new ArrayList<Listener>();
    public void runTask2(){
        DataCenter center = new DataCenter();
        center.addListener(new EventListener("Listener 1"));
        center.addListener(new EventListener("Listener 2"));
        center.addListener(new EventListener("Listener 3"));
        center.trigger("Hello!");
        center.trigger("你好!");
        center.trigger("HaHaHa");
    }

    class DataCenter {
        public void addListener(Listener listener) {
            listeners.add(listener);
        }
        public void trigger(String msg) {
            for (int i=0; i<listeners.size(); i++) {
                listeners.get(i).onEvent(msg);
            }
        }
    }



    class EventListener implements Listener{
        String name;
        public EventListener(String name) {
            this.name = name;
        }
        public void onEvent(String str) {
            System.out.println(name+":"+str);
        }
    }

    interface Listener {
        void onEvent(String str);
    }


}
