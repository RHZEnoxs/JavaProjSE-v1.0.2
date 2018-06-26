package com.enoxs.example.demo;

import java.nio.ByteBuffer;

public class ByteBufferDemo {
    ByteBuffer buf = ByteBuffer.allocate(1024);
    byte [] arr = new byte[1024];
    public ByteBufferDemo(){
        String inpMsg = "123456789ABCDEF";
        buf.put(inpMsg.getBytes());
        show();
        buf.flip();
        show();
        buf.get(arr,0,5);
        System.out.println(new String(arr,0,5));
        show();
        buf.rewind();
        show();
        buf.get(arr,0,5);
        show();
        System.out.println(new String(arr,0,5));
        buf.compact();
        show();
    }
    int flag = 1;
    public void show(){
        System.out.println("flag#" + flag + " " + buf.position() + " , " + buf.limit() + " , " + buf.capacity());
        flag++;
    }

    public static void main(String args[]){
        new ByteBufferDemo();
    }
}
