package com.enoxs.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CalendarDemo {
    SimpleDateFormat sdfv1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    SimpleDateFormat sdfv2 = new SimpleDateFormat("yyyy-MM-dd");


    public CalendarDemo(){
//        runTask1();
//        runTask2();
        runTaskInputDate();
    }
    void runTask1(){
        Date date = new Date();
        String msg = sdfv1.format(date);
        System.out.println(msg);
    }
    void runTask2(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,1);
        String msg = sdfv2.format(cal.getTime()) + "(" + getTimeWeek(cal) + ")";
        System.out.println(msg);
    }
    void runTaskInputDate(){
        Calendar cal = Calendar.getInstance();
        Scanner input = new Scanner(System.in);
        StringBuffer sb = new StringBuffer();
        String date = input.next();// yyyy-MM-dd
        int num = input.nextInt();
        try {
            cal.setTimeInMillis(sdfv2.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Today is a day.
        sb.append(sdfv2.format(cal.getTime()));
        sb.append(" (");
        sb.append(getTimeWeek(cal));
        sb.append(")");
        sb.append("\n");
        for(int i=0;i<num;i++){
            cal.add(Calendar.DATE,1);
            sb.append(sdfv2.format(cal.getTime()));
            sb.append(" (");
            sb.append(getTimeWeek(cal));
            sb.append(")");
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }


    String getTimeWeek(Calendar cal){
        String res = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)){

            case Calendar.SUNDAY:
                res = "日";
                break;
            case Calendar.MONDAY:
                res = "ㄧ";
                break;
            case Calendar.TUESDAY:
                res = "二";
                break;
            case Calendar.WEDNESDAY:
                res = "三";
                break;
            case Calendar.THURSDAY:
                res = "四";
                break;
            case Calendar.FRIDAY:
                res = "五";
                break;
            case Calendar.SATURDAY:
                res = "六";
                break;
        }
        return res;
    }

    public static void main(String args[]){
        new CalendarDemo();
    }
}
