package com.enoxs.example.thread;

import com.enoxs.utillity.SettingUtils;
import org.apache.log4j.Logger;

import javax.net.SocketFactory;
import java.io.*;
import java.net.*;

public class MultiSocketClient {
    private static Logger log = Logger.getLogger(MultiSocketClient.class);
    private SettingUtils setup = new SettingUtils();
    public MultiSocketClient(){
        setup.initLog4j();
        int vipNum = 3;
        for(int i=0;i<vipNum;i++){
            Thread thread = new Thread(new MSCHELLO("MSC#" + i));
            thread.start();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String args []){
        new MultiSocketClient();
    }


    public class MSCHELLO implements Runnable{
        boolean isRunning;
        boolean connected = false;
        boolean connecting = false;
        private String ip = "127.0.0.1";
        private int port = 10118;
        private Socket socket;
        private BufferedReader br;
        private PrintWriter pw;
        private String tag = "";
        private String req = "|HELLO|HI";
        private String res = "";
        int runInterval = 5000;

        public MSCHELLO(String tag){
            isRunning = true;
            this.tag = tag;
            combineReqCommand((this.tag + req));
        }

        private void combineReqCommand(String cmd) {
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

        @Override
        public void run() {
            while (isRunning) {
                try {
                    if (socket != null) {
                    }
                    if (connected) {
                        while (socket.isConnected()) {
                            sendCommand(req);
                            log.info("req -> " + req);
                            res = getResponseLine();
                            log.info("res -> " + res + "\n");
                            if (!isRunning) {
                                break;
                            }
                            Thread.sleep(runInterval);
                        }
                    } else {
                        log.info("try to connect Device");
                        connectDevice(ip,port);
                    }
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
        private void sendCommand(String reqCommand) {
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



}
