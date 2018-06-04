package com.enoxs.example.internet;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import org.apache.log4j.Logger;

public abstract class Client extends Thread{
	private static Logger log = Logger.getLogger(Client.class);
	Thread currentThread;
	/**
	 * Socket I/O
	 */
	protected boolean isRunning;
	protected Socket socket;
	protected InputStream in;
	protected OutputStream out;
	protected BufferedReader br;
	protected PrintWriter pw;
	/**
	 * Server Information
	 */
	protected String IP = "127.0.0.1";
	protected int PORT = 10001;
	protected int TIMEOUT = 30000;

	/**
	 * Connect Parameter
	 */
	protected boolean connected = false;
	protected boolean connecting = false;

	protected int runInterval = 5000;

	protected final static byte CR = 0x0d;
	protected final static byte LF = 0x0a;
	/**
	 * Buffer
	 */
	public char[] buffer = new char[1024];
	public StringBuffer sb = new StringBuffer(1024);


	public Client() {
		log.info("init Client.");
		init();

	}
	public void init(){
		isRunning = true;
	}

	@Override
	public void run() {
		currentThread = Thread.currentThread();
		while (isRunning) {
			try {
				if (socket != null) {
					log.info("start run isConnected=" + connected + ",socket.isConnected()="+socket.isConnected());
				}

				if (connected) {
					String req = "HELLO|DEV|1|REQ|HI " + (char) CR + (char) LF;
					byte [] val = req.getBytes();
					while (socket.isConnected()) {
						log.info("send Command : " + req);
						sendCommand(val);
						receiveSocketEvent();
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
			} catch (SocketException e){
				log.error(e.getMessage(),e);
				connected = false;
			} catch(Exception e){
				log.error(e.getMessage(),e);
				connected = false;
			}
		}
	}



	public void setRemoteAddress(String ip,int port){
		this.IP = ip;
		this.PORT = port;
	}

	public void setSocketTimeOut(int timeout){
		this.TIMEOUT = timeout;
		try {
			this.socket.setSoTimeout(this.TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public boolean stopRun() {
		log.debug("Start to stop Client ..");
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
			currentThread.sleep(5000);
			log.info("connect device  " + IP + ":" + PORT);
			socket = SocketFactory.getDefault().createSocket();
			SocketAddress remoteaddr = new InetSocketAddress(IP, PORT);
			socket.connect(remoteaddr, 10000);
			if (socket.isConnected()) {
				socket.setSoTimeout(15000);
				in = socket.getInputStream();
				out = socket.getOutputStream();
				br = new BufferedReader(new InputStreamReader(in));
				pw = new PrintWriter(new OutputStreamWriter(out));
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

	public void sendCommand(byte [] value) throws IOException {
		out.write(value);
		out.flush();
	}

	public void sendCommand(String reqCommand) {
		pw.print(reqCommand);
		pw.flush();
	}

	public String getResponseLine() throws IOException {
		return br.readLine();
	}

	public abstract void receiveSocketEvent();//customize
		/*Thread.sleep(500);
		while (socket.getInputStream().available() <= 0) {
			count++;
			if (count > 200) {
				canReceive = false;
				break;
			}
			Thread.sleep(100);
		}
		if (canReceive) {
			while(br.ready() || !isEOF){
				isEOF = false;
				size = br.read(buffer);
				line.append(new String(buffer,0,size));
				if((byte)buffer[size-1] == 0x0A){
					isEOF = true;
				}
				Thread.sleep(100);
			}
			LOGGER.info("response=" + line.toString());
			callback.onResponse(line.toString());
			failCont = 0;
		} else {
			LOGGER.error("20 sec not receive data");
			callback.onFailure("fail");
			failCont++;
			if (failCont > 2) {
				close();
			}
		}*/
}
