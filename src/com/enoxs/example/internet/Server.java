package com.enoxs.example.internet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class Server extends Thread{
	private static Logger log = Logger.getLogger(Server.class);
	private static ServerSocket socServer;

	protected boolean isRunning = false;
	protected int serverPort = 10001;
	protected int serverTimeout = 180000;
	protected int clientNo = 0;
	protected int clientLimit = 150;
	protected List<SocketListener> lstSocket= new ArrayList<>();

	protected final static byte CR = 0x0d;
	protected final static byte LF = 0x0a;

	public Server() {
		log.info("init Server.");
		init();
	}

	public void init(){
		try {
			isRunning = true;
			socServer = new ServerSocket(serverPort);
			socServer.setSoTimeout(serverTimeout);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}

	public void run(){
		while(isRunning){
			try {
				Socket socket = socServer.accept();
				SocketListener socListener = new SocketListener(socket);
				socListener.start();
				lstSocket.add(socListener);
				log.info("Socket Connected. count = " + lstSocket.size());
			}catch (SocketTimeoutException e){
			}catch (IOException e) {
				log.error(e.getMessage(),e);
			}
			checkConenctState();
			System.out.println("Client : #" + lstSocket.size());
		}
	}
	public void checkConenctState(){
		int index = 0;
		while(index < lstSocket.size()){
			if(!lstSocket.get(index).isConnected){
				lstSocket.remove(index);
			}else{
				index++;
			}
		}
	}
	
	public class SocketListener extends Thread{
		Thread currentThread;
		boolean isConnected;
		/**
		 * Socket I/O
		 */
		public Socket socket;
		public InputStream in;
		public OutputStream out;
		public BufferedReader br;
		public PrintWriter pw;
		/**
		 * Buffer
		 */
		public char[] buffer = new char[1024];
		public StringBuffer sb = new StringBuffer(1024);
		public int size = 0;
		public int count = 0;

		public SocketListener(Socket socket){
			currentThread = Thread.currentThread();
			isConnected = true;
			this.socket = socket;
			try { 
				in = socket.getInputStream();
				out = socket.getOutputStream();
				br = new BufferedReader(new InputStreamReader(in));
				pw = new PrintWriter(new OutputStreamWriter(out));
			} catch (IOException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}
		}

		@Override
        public void run() {
			try{
				while ((size = br.read(buffer)) > 0) {
					System.out.println(size + " : \n " + String.valueOf(buffer,0,size));
					currentThread.sleep(100);
				}
				close();

			}catch (IOException e){
				log.error(e.getMessage(),e);
			}catch (Exception e){
				log.error(e.getMessage(),e);
			}
        }
        public void sendCommand(byte [] res){
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
				if (pw != null) {
					pw.close();
					pw = null;
				}
				if (br != null) {
					br.close();
					br = null;
				}
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
			checkConenctState();
		}
	}
}
