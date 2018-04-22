package com.enoxs.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.enoxs.utillity.Calculator;
import org.apache.log4j.Logger;

public class SocketServer extends Thread{
	String className = this.getClass().getSimpleName();
	private static Logger log = Logger.getLogger(SocketServer.class);
	boolean isRunning = false;
	ServerSocket socServer;
	int SERVER_PORT = 8081;
	boolean isConnected = false;
	String data1 = "MAYA|SRD|RS|1|STID|";
	String data2 = "10.168.99.65|MDT|2017/10/18 12:29:45";
	final byte CR = 0x0d;
	final byte LF = 0x0a;
	
	
	public SocketServer(int serverPort) {
		log.debug("init SocketServer.");
		// TODO 自動產生的建構子 Stub
		SERVER_PORT = serverPort;
		try {
			isRunning = true;
			socServer = new ServerSocket(SERVER_PORT);
			socServer.setSoTimeout(15000);
			this.start();
		} catch (IOException e) {
//				log.error(className+" "+e.getMessage());
		} catch (Exception e){
			log.error(className + " " + e.getMessage() );
		}
	}
	public void run(){
		while(isRunning){
			try {
				Socket socket = socServer.accept();
				log.info("socket Connected.");
				new SocketListener(socket).start();
			} catch (IOException e) {
				log.error(className+" "+e.getMessage());
			}
			
		}
	}
	
	public class SocketListener extends Thread{
		boolean isConnected = false;
		Socket socket;
		int BUFFER_SIZE = 1024;
		byte[] buffer = new byte[BUFFER_SIZE];
		int read = 0;
		InputStream in;
		OutputStream out;
		Calculator cal = new Calculator();
		public SocketListener(Socket socket){
			this.socket = socket;
			try { 
				in = socket.getInputStream();
				out = socket.getOutputStream();
			} catch (IOException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}
		}
		String msg;
		@Override
        public void run() {
			try{
	        	   while ((read = in.read(buffer)) > 0) {
						if(read!=-1){
							log.debug("read -> " + read);
							String msg = new String(buffer,0,read);
							log.debug("req : " + msg);
							out.write(data1.getBytes());
							Thread.sleep(3000);
							out.write(data2.getBytes());
//							out.write(CR);
//							out.write(LF);
							out.flush();
							Thread.sleep(100);
						}else{
							log.debug("Fail.");
							isConnected = false;
						}
						Thread.sleep(100);
					}
					in.close();
			        in = null ;
			        socket.close();
			        log.info("Client Disconnect.");
	           }catch(Exception e){
	        	   
	           }
        };
	}
        
	

	public static void main(String[] args) {
		// TODO 自動產生的方法 Stub
		new SocketServer(8081);
	}

}
