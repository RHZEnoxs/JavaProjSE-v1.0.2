package com.enoxs.example.internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import com.enoxs.utillity.Calculator;
import com.enoxs.utillity.SettingUtils;
import org.apache.log4j.Logger;

public class SocketServer extends Thread{
	private static Logger log = Logger.getLogger(SocketServer.class);
	private SettingUtils setup = new SettingUtils();
	private static ServerSocket socServer;
	private boolean isRunning = false;
	private int SERVER_PORT = 10001;
	private int TIMEOUT = 1800000;


	public byte[] res = {'R' , 'E' , 'S' , '|'
			, 'H' , 'E' , 'L' , 'L' , 'O' , '|'
			, 'O' , 'K' , CR , LF  };
	final static byte CR = 0x0d;
	final static byte LF = 0x0a;
	
	
	public SocketServer() {
		setup.initLog4j();
		log.info("init SocketServer.");
		try {
			isRunning = true;
			socServer = new ServerSocket(SERVER_PORT);
			socServer.setSoTimeout(TIMEOUT);
			this.start();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} catch (Exception e){
			log.error(" -> " + e.getMessage() , e);
		}
	}

	public void run(){
		while(isRunning){
			try {
				Socket socket = socServer.accept();
				log.info("Socket Connected.");
				new SocketListener(socket).start();
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
	}
	
	public class SocketListener extends Thread{
		private boolean isConnected = false;
		private Socket socket;
		private int BUFFER_SIZE = 1024;
		private byte[] buffer = new byte[BUFFER_SIZE];
		private String msg = "";
		private int read = 0;
		private InputStream in;
		private OutputStream out;
		StringBuffer sb = new StringBuffer(1024);
		public SocketListener(Socket socket){
			isConnected = socket.isConnected();
			this.socket = socket;
			try { 
				in = socket.getInputStream();
				out = socket.getOutputStream();
			} catch (IOException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}
		}

		@Override
        public void run() {
			try{
				while ((read = in.read(buffer)) > 0) {
					if(read!=-1){
						System.out.println("read -> " + read);
						sb.setLength(0);
						for(int i=0;i<read;i++){
							if(CR == buffer[i]){
								sb.append("<CR>");
							}else if(LF == buffer[i]){
								sb.append("<LF>");
							}else{
								sb.append(buffer[i]);
							}
						}
						System.out.println("req -> " + sb.toString());
						send(res);
						Thread.sleep(100);
					}else{
						close();
					}
					Thread.sleep(100);
				}
			}catch (IOException e){
				log.error(e.getMessage(),e);
			}catch (Exception e){
				log.error(e.getMessage(),e);
			}
        }
        public void send(byte [] res){
			try {
				out.write(res);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (Exception e){
				log.error(e.getMessage(),e);
			}
		}
        public void close(){
			try {
				if(in != null){
					in.close();
					in = null ;
				}
				if(out != null){
					out.close();
					out = null;
				}
				if(socket != null){
					socket.close();
					socket = null;
				}
				isConnected = false;
			}catch (IOException e){
				log.error(e.getMessage(),e);
			}catch (Exception e){
				log.error(e.getMessage(),e);
			}
			log.info("Client Disconnect.");
		}
	}
        
	

	public static void main(String[] args) {
		// TODO 自動產生的方法 Stub
		new SocketServer();
	}

}
