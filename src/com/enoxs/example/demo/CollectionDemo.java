package com.enoxs.example.demo;

import com.enoxs.utillity.Calculator;

import java.util.*;

public class CollectionDemo {
    Calculator cal = new Calculator();
    public CollectionDemo(){
        runMapDemo();
    }
    void runMapDemo(){
        Map<String,String> map = new HashMap<>();
        map.put("K1","R1");
        map.put("K2","R2");
        map.put("K3","R3");
        System.out.println(map);

        for (String key : map.keySet()) {
            if(map.get(key).equals("R2")){
                System.out.print("R2 is " + key);
                break;
            }
        }

    }
    void runListDemo(){
        List<Byte> lstVal = new ArrayList<>();
        lstVal.add((byte)0x31);
        lstVal.add((byte)0x32);
        lstVal.add((byte)0x33);
        System.out.println(cal.ByteToHexString(lstVal));

        Object [] arr = lstVal.toArray();
        for(int i=0;i<arr.length;i++){
            System.out.print(cal.BytesToHexString((byte)arr[i]) + " ");
        }

        System.out.println();

        List<Integer> numbers = new ArrayList<Integer> ();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        Integer[] array = numbers.toArray (new Integer [numbers.size()+2]);
        System.out.print(Arrays.toString(array));
    }

    public static void main(String args[]){
        new CollectionDemo();
    }
}
