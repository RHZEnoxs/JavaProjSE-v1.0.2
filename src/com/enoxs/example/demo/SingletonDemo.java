package com.enoxs.example.demo;

public class SingletonDemo {
    private static SingletonDemo ourInstance = new SingletonDemo();

    public static SingletonDemo getInstance() {
        return ourInstance;
    }

    private SingletonDemo() {

    }

    public static void main(String args[]){

    }
}
