package com.enoxs.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
import java.util.Properties;

import javax.net.SocketFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
public class SocketClient extends Thread{
	private static Logger log = Logger.getLogger(SocketClient.class);
	String deviceIp = "10.168.98.35";
	int devicePort = 8081;
	boolean isRunning = false;
	boolean connected = false;
	boolean connecting = false;
	Socket socket;
	BufferedReader br;
	PrintWriter pw;
	int runInterval = 5000;
	String reqCommand = "";
	int fail_data = 0;
	public SocketClient() {
		combineReqCommand();
		runInterval = 5000;
		isRunning = true;
		log.debug("start run");
		this.start();
	}
	public void initRun() {
		combineReqCommand();
		runInterval = 5000;
		isRunning = true;
	}

	private void combineReqCommand() {
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
		reqCommand = "K" + cr + lf;
	}

	public boolean checkConnect() {
		if (!connected || !connecting) {
				connectDevice(deviceIp,devicePort);
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

			log.debug("Dbb27 stopRun");
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return isRunning;
	}

	public void connectDevice(String ip	,int port) {
		log.debug("start connect device");
		try {
			connecting = true;
			Thread.sleep(5000);
			log.info("connect device  " + ip + ":" + port);
			socket = SocketFactory.getDefault().createSocket();
			SocketAddress remoteaddr = new InetSocketAddress(ip,port);
			socket.connect(remoteaddr, 10000);
			if (socket.isConnected()) {
				socket.setSoTimeout(5000);
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

	public void run() {
		log.debug("begin run");
		while (isRunning) {
			try {
//				livetimer = 15;
				if (socket != null) {
					log.info("start run isConnected=" + connected + ",socket.isConnected()="+socket.isConnected());
				}
				if (connected) {
					while (socket.isConnected()) {
						log.info("try to request Data");
						sendCommand(reqCommand);
						String res = getResponseLine();
						if (!res.equals("")) {
							log.info("receive data : " + res );
						}else{
							fail_data++;
							log.debug("\nDialysis: There is no data\n");
							if (fail_data > 3) {
								connected = false;
							}
						}
						if (!isRunning) {
							break;
						}
						Thread.sleep(runInterval);
					}
				} else {
//					isConnected = false;
					log.info("try to connect Device");
					connectDevice(deviceIp,devicePort);
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


	private void sendCommand(String reqCommand) {
		pw.print(reqCommand);
		pw.flush();
	}

	public String getResponseLine() {
		String line = "";
		try {
			line = br.readLine();
		} catch (IOException e) {
			System.out.println("getResponseStr exception: " + e);
		}
		return line;
	}

	
	public static void main(String[] args) {
		// TODO 自動產生的方法 Stub
		Properties prob = new Properties();
		try {
        	FileInputStream fis = new FileInputStream("res/config/log4j.properties");
            prob.load(fis);
            fis.close();
            fis = null;
            
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
		PropertyConfigurator.configure(prob);
		new SocketClient();
	}

}
