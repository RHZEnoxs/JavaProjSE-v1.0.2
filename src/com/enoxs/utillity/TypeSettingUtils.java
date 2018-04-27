package com.enoxs.utillity;

public class TypeSettingUtils {

    public TypeSettingUtils(){

    }
    /**
     * String to Array
     */
    public String[] splitMsgToArray(String rawdata,String splitter){
        String [] tempArray = rawdata.split(splitter,-1);
        return tempArray;
    }

    /**
     * Show String Array code
     * Copy
     */
    public String showStringArray(String [] msg,int lf,boolean isComma){
        StringBuffer sb = new StringBuffer(512);
        for(int i=0;i<msg.length;i++){
            sb.append(msg[i]);
            if(i != msg.length-1 && isComma){
                sb.append(" , ");
            }
            if((i+1) % lf == 0 && i != msg.length-1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }


}
