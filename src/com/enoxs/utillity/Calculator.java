package com.enoxs.utillity;

import java.util.List;

public class Calculator {//Calculate_Tools

	public Calculator() {
	}
	private StringBuffer msg_sb = new StringBuffer();
	// --- Cal Tools
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public char [] BytesToHexChar(byte[] bytes) { // no use ...
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return hexChars;
	}
	public byte[] HexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	public byte HexStringToByte(String s) {
		int len = s.length();
		return ((byte) ((Character.digit(s.charAt(0), 16) << 4)
		                    + Character.digit(s.charAt(1), 16)));
	}
	public String BytesToHexString(byte b) {
		return ("" + "0123456789ABCDEF".charAt(0xf & b >> 4) + "0123456789ABCDEF"
		                .charAt(b & 0xf));
		}
	public String ByteToHexString(List<Byte> list){
		msg_sb.delete(0,msg_sb.length());
		for(int i=0;i<list.size();i++){
			msg_sb.append(BytesToHexString(list.get(i)));
		}
		return msg_sb.toString();
	}
	public String ByteToHexString(byte [] arr){
		msg_sb.delete(0,msg_sb.length());
		for(int i=0;i<arr.length;i++){
			msg_sb.append(BytesToHexString(arr[i]));
		}
		return msg_sb.toString();
	}
	public byte[] ListByteToByte(List<Byte> list){
		byte [] val_arr = new byte [list.size()] ;
		for(int i=0;i<val_arr .length;i++){
			val_arr[i] = list.get(i);
		}
		return val_arr;
	}
	
	public int Byte2Short(byte b1, byte b2) {// 雿�����
		if ((b2 >> 7) == 0)
			return ((b1 & 0xff) | ((b2 << 8) & 0xff00));
		else
			return (((b1 & 0xff) | ((b2 << 8) & 0xff00)) & 0x7FFF) * -1;
	}
	public int Byte2Int(byte b1, byte b2) {// Word 16 bit
		return (b1 & 0xff) | ((b2 << 8) & 0xff00);
	}
	
	
	
	public int ByteToDecimal(byte b) {
		return b & 0xff;
	}
	public int HexStringToDecimal(String hex) {
		return (Integer.valueOf(hex, 16));
	}


	public int hexStringToDecimal(String _hex) {
        return (Integer.valueOf(_hex, 16));
    }

	public static void main(String[] args) {
	}

}
