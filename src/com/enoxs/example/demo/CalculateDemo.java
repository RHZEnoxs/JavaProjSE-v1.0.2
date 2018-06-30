package com.enoxs.example.demo;

import com.enoxs.utillity.Calculator;

public class CalculateDemo {
    Calculator cal = new Calculator();
    public CalculateDemo(){
        int value = 2494;
        byte [] arr = integerHighLow(value);
        System.out.println(cal.BytesToHexString(arr[0]));
        System.out.println(cal.BytesToHexString(arr[1]));

        byte highByte = (byte)0x09;
        byte lowByte = (byte)0xBE;
        int num = byte2Integer(highByte,lowByte);
        System.out.println("Number : " + num);

    }

    byte [] integerHighLow(int val){
        byte [] arr = new byte[2];
        byte highByte = (byte)((val >> 8) & 0xFF);
        byte lowByte = (byte)(val & 0xFF);
        arr[0] = highByte;
        arr[1] = lowByte;
        return arr;
    }
    int byte2Integer(byte hightByte,byte lowByte){
        return ((int)hightByte << 8) + ((int)lowByte & 0xFF);
    }

    void calAND(){
        System.out.printf("0 AND 0\t\t%d\n", 0 & 0);
        System.out.printf("0 AND 1\t\t%d\n", 0 & 1);
        System.out.printf("1 AND 0\t\t%d\n", 1 & 0);
        System.out.printf("1 AND 1\t\t%d\n\n", 1 & 1);
    }

    void printf(String msg){
        System.out.print(msg);
    }

    public static void main(String args[]){
        new CalculateDemo();
    }
}
