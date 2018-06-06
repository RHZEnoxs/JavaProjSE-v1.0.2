package com.enoxs.utillity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
public abstract class RXTXComm implements SerialPortEventListener{
	protected ByteBuffer mDataBuffer = ByteBuffer.allocate(1024);
	private String responseMsg = "";
	public RXTXComm(){
	}
	
	String message="";
	SerialPort serialPort;
	 CommPortIdentifier portIdentifier;
	 CommPort commPort;
	 
	 //String COMPORT="COM18";
	 int BaudRate=9600;
	 int DATABITS=8;
	 int STOPBITS=1;
	 int PARITY=0;
	 
	 public void setBaudRate(int baudRate) {
		BaudRate = baudRate;
	}
	 public void setDATABITS(int dATABITS) {
		DATABITS = dATABITS;
	}
	 public void setSTOPBITS(int sTOPBITS) {
		STOPBITS = sTOPBITS;
	}
	public void setPARITY(int pARITY) {
		PARITY = pARITY;
	}
	 
	 public void stop() {
		 serialPort.close();
		 serialPort=null;
		 portIdentifier=null;
		 commPort.close();
		 commPort=null;
		 System.gc();

	}
	 
	public String getMessage() {
		return message;
	}
	
	public SerialPort getSerialPort() {
		return serialPort;
	}
	public InputStream input_data;
	public OutputStream output_data;
    public  void  connect ( String portName ) throws Exception
    {
    	try{
         portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
	    } catch (NoSuchPortException ex) {
	        System.out.println("No Such Port:" + ex);
	    	stop();
	     }

        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            commPort = portIdentifier.open(this.getClass().getName(),2000);
            if ( commPort instanceof SerialPort )
            {
                //SerialPort serialPort = (SerialPort) commPort;
            	  serialPort = (SerialPort) commPort;
            	  System.out.print("portName="+portName);
            	  System.out.print(",BaudRate="+BaudRate);
            	  System.out.print(",DATABITS="+DATABITS);
            	  System.out.print(",STOPBITS="+STOPBITS);
            	  System.out.println(",PARITY="+PARITY);
            	  serialPort.setSerialPortParams(BaudRate,DATABITS,STOPBITS,PARITY);
            	  input_data = serialPort.getInputStream();
                  output_data = serialPort.getOutputStream();      
                  serialPort.addEventListener(this);
                  serialPort.notifyOnDataAvailable(true);
            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    
    /**
     * Handles the input coming from the serial PORT. A new line character
     * is treated as the end of runTask1 block in this example.
     */
    public StringBuffer msg_sb = new StringBuffer();
    public StringBuffer hex_sb = new StringBuffer();
    public byte[] buffer = new byte[1024];
    public void serialEvent(SerialPortEvent arg0) {
    	String m="";
        int size;
        try {
            if (input_data == null) return;
            size = input_data.read(buffer);
            if (size > 0) {
                for(int i=0;i<size;i++) {
                	if(buffer[i] == 0x06){
                		System.out.println("ACK ");
                	}
                	msg_sb.append(toHex(buffer[i])+"");
                }
                if(buffer[size -1 ] == (byte)0x04){
                	  System.out.println("EOT");
                 }
                System.out.println(msg_sb.toString());
                msg_sb.delete(0 , msg_sb.length());
                System.out.println( "least:  " + msg_sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
   public String toHex(byte b) {
        return ("" + "0123456789ABCDEF".charAt(0xf & b >> 4) + "0123456789ABCDEF"
                .charAt(b & 0xf));
    }
   public String cleanResponseMsg(){
	   String _s = responseMsg;
	   responseMsg = "";
	   return _s;
   }
   public boolean sendCommand(byte _byte){
			try {
				output_data.write(_byte);
				output_data.flush();
				return true;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		
		return false;
	}
   public boolean sendCommand(byte []_byte){
		try {
			output_data.write(_byte);
			output_data.flush();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	
	return false;
}
   public List ComPortList(){
 	    List<String> list = new ArrayList();
 	    Enumeration portList = CommPortIdentifier.getPortIdentifiers();
 	    while (portList.hasMoreElements()) {
 	        CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
 	        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
 	            list.add(portId.getName());
 	        }
 	    }
 	    return list;
 	}
}
