package com.enoxs.example.thread;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class MessageConsoleCenter extends Thread{
    private static Logger log = Logger.getLogger(MessageConsoleCenter.class);

    /**
     * Task
     * 主任務 監聽 Port 10117 連線
     * 副任務 發送 Broadcast 訊息
     */

    private static ServerSocket socServer;
    private boolean isRunning = false;
    private int socPort = 10117;
    private int socTimeout = 1800000;
    private int limitMsgConsole = 5;
    public List<MsgConsole> lstMsgConsole;

    /**
     * ALL Client Response Message.
     */

    static Thread currentThread;
    static StringBuffer broadcastMessage = new StringBuffer(1024);//SBD
    /**
     * Broadcast Thread
     */

    int broadcastInterval = 10000;// 15 sec
    int connectNumber = 0;

    public void postMessage(String msg){
        broadcastMessage.append(msg);
    }


    public MessageConsoleCenter(){
        log.info("init Server.");
        currentThread = Thread.currentThread();
        Thread threadSendBroadcast = new Thread(new BroadcastRunnable());
        threadSendBroadcast.start();
        try {
            isRunning = true;
            socServer = new ServerSocket(socPort);
            socServer.setSoTimeout(socTimeout);
            this.start();
        }catch (SocketTimeoutException e){

        }catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (Exception e){
            log.error(e.getMessage() , e);
        }
    }

    Socket mSocket;
    MsgConsole mSocListener;
    @Override
    public void run(){
        lstMsgConsole = new ArrayList<>();
        while(isRunning){
            if(lstMsgConsole.size() < limitMsgConsole){
                try {
                    mSocket = socServer.accept();
                    log.info("Socket Connected.");
                    mSocListener = new MsgConsole(mSocket);
                    lstMsgConsole.add(mSocListener);
                    sleepMilliSec(5000);
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                } catch (Exception e){
                    log.error(e.getMessage(),e);
                }
            }
            sleepMilliSec(5000);
        }
    }


    public void sleepMilliSec(int time){
        try {
            currentThread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public class BroadcastRunnable implements Runnable{
        public boolean isRunning;
        private Thread currentThread;
        public BroadcastRunnable(){
            this.isRunning = true;
            this.currentThread = Thread.currentThread();
        }
        @Override
        public void run() {
            while(isRunning){
                try {
                    if(lstMsgConsole != null){
                        connectNumber = lstMsgConsole.size();
                        if(connectNumber > 0){
                            for(int i=0;i<connectNumber;i++){
                                lstMsgConsole.get(i).sendBroadcast(broadcastMessage.toString());
                            }
                        }
                    }
                    broadcastMessage.setLength(0);
                    currentThread.sleep(broadcastInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class MsgConsole {
        private Socket socket;
        private OutputStream output;
        private PrintWriter printWriter;

        public MsgConsole(Socket socket){
            this.socket = socket;
            try {
                output = socket.getOutputStream();
                printWriter = new PrintWriter(output, true);
            } catch (IOException e) {
                // TODO 自動產生的 catch 區塊
                e.printStackTrace();
            }
        }

        public void sendBroadcast(String res){
            try {
                printWriter.print(res);
                printWriter.flush();
            }catch (Exception e){
                closeSocket();
                log.error(e.getMessage(),e);
            }
        }

        public void closeSocket(){
            try {
                if(output != null){
                    output.close();
                    output = null;
                }
                if(socket != null){
                    socket.close();
                    socket = null;
                }
            }catch (IOException e){
                log.error(e.getMessage(),e);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
            log.info("Client Disconnect.");
        }
    }
}
