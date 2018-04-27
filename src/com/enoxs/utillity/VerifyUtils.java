package com.enoxs.utillity;

public class VerifyUtils {

    public Calculator cal = new Calculator();
	public VerifyUtils() {
		// TODO 自動產生的建構子 Stub
	}

    public int crcGetByte(String _hex) {
        int crc = 0x00; //initial value
        if (!_hex.equals("")) {
            int size = _hex.length() / 2;
            for (int i=0; i<size; i++) {
                String s = _hex.substring(i*2, i*2+2);
                int n = cal.hexStringToDecimal(s);
                crc += n;
            }
            crc %= 256;
        }
        return crc;
    }
    public int crcGet16CCIT(String _hex) {
        int crc = 0x0000; //initial value
        int polynomial = 0x1021; //0001 0000 0010 0001 (0, 5, 12)
        if (!_hex.equals("")) {
            byte[] bs = cal.HexStringToByteArray(_hex);
            for (byte b : bs) {
                for (int i = 0; i < 8; i++) {
                    boolean bit = ((b   >> (7-i) & 1) == 1);
                    boolean c15 = ((crc >> 15    & 1) == 1);
                    crc <<= 1;
                    if (c15 ^ bit) crc ^= polynomial;
                }
            }
            crc &= 0xffff;
        }
        //swab
        return (((crc & 0xFF00) >> 8) + ((crc & 0xFF) << 8));
    }

    public boolean verityCrcByte(StringBuffer _sb) {
        boolean isChecked = false;
        int len = _sb.length();
        if (len > 0) {
            int srcCrcByte = cal.hexStringToDecimal(_sb.substring(len-2, len));
            int calCrcByte = crcGetByte(_sb.substring(0, len-2));
            //log.debug("crcGetByte:" + Integer.toString(srcCrcByte)
            //        + " to " + Integer.toString(calCrcByte, 16));
            if (srcCrcByte == calCrcByte) {
                isChecked = true;
            }
        }
        return isChecked;
    }
	
	private boolean verifyIdentity(String id){
		if (!id.matches("[a-zA-Z]\\d{9}")){
			return false;
		}
        id = id.toUpperCase();                        //轉大寫
        char first = id.charAt(0);              //取出第一個字母
         /*A-Z的對應數字*/
        int idum[] = {10,11,12,13,14,15,16,17,34,18,19,20,21
                ,22,35,23,24,25,26,27,28,29,32,30,31,33};
         /*轉成11碼的字串,'A'=65；
            substring:從index:1開始取String*/
        id = idum[first - 'A']+id.substring(1);

         /*把第一碼放到sum中,'0'=48*/
        int sum = id.charAt(0)-'0';

         /*取2-10的總合*/
        for(int i=1;i<10;i++){
            sum +=id.charAt(i)*(10-i);
        }
         /*10-加總的尾數 = 第11碼*/
        int checksum = (10-(sum%10))%10;
        if (checksum == id.charAt(10)-'0'){
            return true;
        }else{
            return false;
        }
    }
}
