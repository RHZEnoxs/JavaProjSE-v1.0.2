package com.enoxs.example.demo;

public class TipDemo {

    public TipDemo(){
        runTaskv1();
        runTaskv2();
        runTaskv3();
    }

    /**
     * Tip 1 Return this.
     */
    void runTaskv1(){
        System.out.println("Demo #Tip1.");

        ViewPage viewPage = new ViewPage();
        viewPage.setText("Hello").setSize(5).showInfo();
    }
    class ViewPage{
        int size;
        String text;
        ViewPage setSize(int size){
            this.size = size;
            return this;
        }
        ViewPage setText(String text){
            this.text = text;
            return this;
        }
        void showInfo(){
            System.out.println(text + " , " + size);
        }

    }
    /**
     * Tip 2 隱式的繼承覆蓋 (內部匿名類別)
     */
    void runTaskv2(){
        System.out.println("Demo #Tip2.");
        new MessageEvent().onEvent();
        new MessageEvent(){
            public Event onEvent(){
                System.out.println("A New Event Message.");
                return this;
            }

        }.onEvent();
    }


    abstract class Event{
        abstract Event onEvent();
    }
    class MessageEvent extends Event{
        public Event onEvent() {
            System.out.println("A Event Message.");
            return this;
        }
    }
    /**
     * Tip 3 內部類別
     */
    void runTaskv3(){
        System.out.println("Demo #Tip3.");
        XButton.OnClickListener listenerV1 = new XButton.OnClickListener();
        listenerV1.onClick();
        XButton.OnClickListener listenerV2 = new XButton.OnClickListener(){
            @Override
            public void onClick() {
                System.out.println("listener2.OnClick()");
            }
        };
        listenerV2.onClick();
    }
    public static class XButton{
        public static class OnClickListener {
            // 內部類別的成員函數
            public void onClick() {
                System.out.println("Button.OnClickListener.OnClick()");
            }
        }
    }


    public static void main(String args[]){
        new TipDemo();
    }
}
