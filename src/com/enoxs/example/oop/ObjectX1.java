package com.enoxs.example.oop;

public class ObjectX1 extends AbstractX implements InterfaceX {
    public ObjectX1(){
        Object obj = new Object();
        runTask1();
        runTask2(obj);
        runTask3(obj,obj);
        runTask4();
        runTask5(obj);
        runTask6(obj,obj);
    }
    @Override
    public void runTask1() {
        System.out.println("Task1.");
    }

    @Override
    public void runTask2(Object obj) {
        System.out.println("Task2.");
    }

    @Override
    public void runTask3(Object obj1, Object obj2) {
        System.out.println("Task3.");
    }

    @Override
    public void runTask4() {
        System.out.println("Task4.");

    }

    @Override
    public void runTask5(Object obj) {
        System.out.println("Task5.");
    }

    @Override
    public void runTask6(Object obj1, Object obj2) {
        System.out.println("Task6.");
    }
    public static void main(String args[]){
        new ObjectX1();
    }
}
