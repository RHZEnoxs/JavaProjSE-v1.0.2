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
    private boolean isComma = true;
    private boolean isNumber = true;
    private boolean isQuotationMarks = false;
    private  int lf = 1;
    public void setStringArrayFormat(int lf , boolean isNumber,boolean isQuotationMarks,boolean isComma){
        setNextLine(lf);
        setFormateNumber(isNumber);
        setFormatQuotationMarks(isQuotationMarks);
        setFormatComma(isComma);

    }
    public void setNextLine(int lf){
        this.lf = lf;
    }
    public void setFormateNumber(boolean isNumber){
        this.isNumber = isNumber;
    }
    public void setFormatComma(boolean isComma){
        this.isComma = isComma;
    }
    public void setFormatQuotationMarks(boolean isQuotationMarks){
        this.isQuotationMarks = isQuotationMarks;
    }
    public String showStringArray(String [] msg){
        StringBuffer sb = new StringBuffer(512);
        for(int i=0;i<msg.length;i++){
            if(this.isNumber){
                sb.append((i + 1) + " : ");
            }
            if(isQuotationMarks){
                sb.append("\"");
            }
            sb.append(msg[i]);
            if(isQuotationMarks){
                sb.append("\"");
            }
            if(i != msg.length-1 && this.isComma){
                sb.append(" , ");
            }
            if((i+1) % this.lf == 0 && i != msg.length-1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }


}
