package com.enoxs.utillity;

public class VerifyData {

	public VerifyData() {
		// TODO 自動產生的建構子 Stub
	}
	
	private boolean checkIdentity(String id){
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
