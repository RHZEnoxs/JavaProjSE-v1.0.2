package com.enoxs.example.rs232;

import java.io.IOException;
import java.util.Scanner;

import com.enoxs.utillity.Calculator;
import com.enoxs.utillity.FileOperator;
import com.enoxs.utillity.RXTXComm;
import gnu.io.SerialPortEvent;
import org.apache.log4j.Logger;

public class Device extends RXTXComm {
	private static Logger log = Logger.getLogger(Device.class);
	String CommPort = "/dev/tty.usbserial";
//	String CommPort = "COM3";
	Calculator cal = new Calculator();
	FileOperator foe = new FileOperator();
	public Device() {
		initDevice();
		controlCenter();
	}
	private void initDevice(){
		try {
			try{
				setBaudRate(2400);
				connect(CommPort);
			}catch(NullPointerException e){
				e.printStackTrace();
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	byte [] COMM_REQ_DATA = {
			'K' , 0x0D , 0x0A
	};
	public void controlCenter() {
		Scanner input = new Scanner (System.in);
		String ctrl ;
		while(true){
        	ctrl = input.next();	
        	switch(ctrl){
        		case "exit":
        			System.exit(0);
        			break;
        		case "K":
        			sendCommand(COMM_REQ_DATA);
        			break;
        		default:
        			System.out.println("Unknow Ctrl Cmd");
        			break;
        	}
        }
	}
	
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO 自動產生的方法 Stub
        int size;
        try {
            if (input_data == null) return;
            size = input_data.read(buffer);
            if (size > 0) {
            	mDataBuffer.put(buffer,0, size);
            	log.info(mDataBuffer.toString());
            	mDataBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		new Device();
	}

}
