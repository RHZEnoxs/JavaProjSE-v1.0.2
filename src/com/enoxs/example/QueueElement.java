package com.enoxs.example;

import java.util.LinkedList;
import java.util.Queue;

public class QueueElement {

	public QueueElement() {
		// TODO 自產生的建構子 Stub
//		example();
		exampleArray();
	}
	public void exampleArray(){
		String [] a = {"a1","a2","a3"};
		String [] b = {"b1","b2","b3"};
		String [] c = {"c1","c2","c3"};
		
		Queue<String []> queue = new LinkedList<String[]>();
		queue.add(a);
		queue.add(b);
		queue.add(c);
		for(String[] q : queue){
            System.out.println(q[0]);
        }
		String[] temp = queue.poll();
		System.out.println("poll="+temp[1]); //返回第一個元素，並在隊列中刪除
        for(String[] q : queue){
            System.out.println(q[1]);
        }
	}
	public void example(){
		//add()和remove()方法在失敗的時候會拋出異常(不推薦)
        Queue<String> queue = new LinkedList<String>();
        //添加元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        System.out.println("poll="+queue.poll()); //返回第一個元素，並在隊列中刪除
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        System.out.println("element="+queue.element()); //返回第一個元素 
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        System.out.println("peek="+queue.peek()); //返回第一個元素 
        for(String q : queue){
            System.out.println(q);
        }
	}

	public static void main(String[] args) {
		// TODO 自動產生的方法 Stub
		new QueueElement();
	}

}
