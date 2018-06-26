package com.enoxs.example.demo;

import java.util.Arrays;

public class ArraysDemo {
    int[] arr = {93, 5, 3, 55, 57, 7, 2 ,73, 41, 91};
    int[] val = {93, 5, 3, 55, 57, 7, 2 ,73, 41, 91};
    int pos,key;
    public ArraysDemo(){
        /**
         * Equals
         */
        if(Arrays.equals(arr, val)){
            System.out.println("Equal!!");
        }
        /**
         * Search
         */
        key = 73;
        pos = Arrays.binarySearch(arr, key);
        System.out.println(pos + " -> " + arr[pos]);
        System.out.println(Arrays.toString(arr));
        /**
         * Sort
         */
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        /**
         * Fill
         */
        Arrays.fill(arr,(byte)0x00);
        System.out.println(Arrays.toString(arr));
        /**
         * copyOf
         */
        int [] tempArray = Arrays.copyOf(val,3);
        System.out.println(Arrays.toString(tempArray));
    }
    public static void main(String args[]){
        new ArraysDemo();
    }
}
