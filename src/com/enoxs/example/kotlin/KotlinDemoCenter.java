package com.enoxs.example.kotlin;

public class KotlinDemoCenter {

    public KotlinDemoCenter(){
        ApInfo info = new ApInfo(1,"Enoxs",true);
        System.out.println(info.getId() + " , " + info.getName() + " , " + info.getState());
    }

    public static void main(String args[]){
        new KotlinDemoCenter();
    }
}
