package com.enoxs.example.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import com.enoxs.utillity.SettingUtils;
import org.apache.log4j.Logger;

public abstract class SocketClient extends Thread{
	private static Logger log = Logger.getLogger(SocketClient.class);
	private SettingUtils setup = new SettingUtils();


	public boolean isRunning;
	public Socket socket;
	public BufferedReader br;
	public PrintWriter pw;

	private String ip = "127.0.0.1";
	private int port = 10001;
	private int timeout = 30000;

	final static byte CR = 0x0d;
	final static byte LF = 0x0a;



	boolean connected = false;
	boolean connecting = false;
	public String req = "";
	public String res = "";
	int runInterval = 5000;
	int faiCount = 0;

	public SocketClient() {
		/*setup.initLog4j();
		log.info("init SocketClinet.");
		combineReqCommand("K");
		isRunning = true;
		this.start();*/
	}

	public void setRemoteAddress(String ip,int port){
		this.ip = ip;
		this.port = port;
	}

	public void setSocketTimeOut(int timeout){
		this.timeout = timeout;
		try {
			this.socket.setSoTimeout(this.timeout);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (isRunning) {
			try {
				if (socket != null) {
					log.info("start run isConnected=" + connected + ",socket.isConnected()="+socket.isConnected());
				}
				if (connected) {
					while (socket.isConnected()) {
						log.info("req -> " + req);
						sendCommand(req);
						res = getResponseLine();
						if (!res.equals("")) {
							log.info("receive data : " + res );
						}else{
							faiCount++;
							if (faiCount > 10) {
								connected = false;
							}
						}
						if (!isRunning) {
							break;
						}
						Thread.sleep(runInterval);
					}
				} else {
					log.info("try to connect Device");
					connectDevice();
				}
				log.debug("end run isConnected=" + connected + ",socket.isConnected()="+socket.isConnected());
				if (!socket.isConnected()) {
					connected = false;
				}
			} catch (InterruptedException e) {
				log.error(e.getMessage(),e);
				connected = false;
			} catch(Exception e){
				log.error(e.getMessage(),e);
				connected = false;
			}
		}
	}


	public void combineReqCommand(String cmd) {
		String cr = "";
		String lf = "";
		byte[] bt1 = new byte[1];
		try {
			bt1[0] = 13;
			cr = new String(bt1, 0, 1, "ASCII");
			bt1[0] = 10;
			lf = new String(bt1, 0, 1, "ASCII");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
		}
		req = cmd + cr + lf;
	}

	public boolean checkConnect() {
		if (!connected || !connecting) {
				connectDevice();
		}
		return connected;
	}


	public boolean isConnect() {
		return connected;
	}


	public boolean stopRun() {
		log.debug("Start to stop SocketClient ..");
		try {
			isRunning = false;
			if (br != null) {
				br.close();
				br = null;
			}
			if (pw != null) {
				pw.close();
				pw = null;
			}
			if(!socket.isClosed()) {
				socket.close();
				socket = null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return isRunning;
	}

	public void connectDevice() {
		log.debug("start connect device");
		try {
			connecting = true;
			Thread.sleep(5000);
			log.info("connect device  " + ip + ":" + port);
			socket = SocketFactory.getDefault().createSocket();
			SocketAddress remoteaddr = new InetSocketAddress(ip,port);
			socket.connect(remoteaddr, 10000);
			if (socket.isConnected()) {
				socket.setSoTimeout(15000);
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				connected = true;
			} else {
				connected = false;
			}
			connecting = false;
		}catch(SocketException e){
			log.error(e.getMessage(),e);
			connected = false;
			connecting = false;
		} catch (UnknownHostException e) {
			log.error(e.getMessage(),e);
			connected = false;
			connecting = false;
		} catch (SocketTimeoutException e) {
			log.error(e.getMessage(),e);
			connected = false;
			connecting = false;
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			connected = false;
			connecting = false;
		} catch (InterruptedException e) {
			log.error(e.getMessage(),e);
			connected = false;
			connecting = false;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			connected = false;
			connecting = false;
		}
	}


	public void sendCommand(String reqCommand) {
		pw.print(reqCommand);
		pw.flush();
	}

	public String getResponseLine() {
		String line = "";
		try {
			line = br.readLine();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			System.out.println("getResponseStr exception: " + e);
		}
		return line;
	}
}
