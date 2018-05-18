package com.enoxs.example.thread;

import com.enoxs.utillity.SettingUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MessageConsoleServer {
    private static Logger log = Logger.getLogger(MessageConsoleServer.class);
    SettingUtils settingUtils = new SettingUtils();
    public MessageConsoleServer(){
        settingUtils.initLog4j();
        MessageConsoleCenter center = new MessageConsoleCenter();
        SocketServer server = new SocketServer();
        server.setMessageConsoleCenter(center);

        Scanner input = new Scanner(System.in);
        String ctrl;
        while(true){
            ctrl = input.next();
            switch (ctrl){
                case "1":
                    break;
                case "2":
                    break;
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }
    public class SocketServer extends Thread{
        Thread currentThread;

        private ServerSocket socServer;
        private boolean isRunning = false;
        private int SERVER_PORT = 10118;
        private int TIMEOUT = 1800000;
        private int clientCount = 0;
        private MessageConsoleCenter center;
        public SocketServer(){
            try {
                isRunning = true;
                socServer = new ServerSocket(SERVER_PORT);
                socServer.setSoTimeout(TIMEOUT);
                this.start();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        public void setMessageConsoleCenter(MessageConsoleCenter center){
            this.center = center;
        }

        @Override
        public void run(){
            this.currentThread = Thread.currentThread();
            while(isRunning){
                try {
                    Socket socket = socServer.accept();
                    ClientWorker client = new ClientWorker(socket,clientCount);
                    client.setMessageConsoleCenter(this.center);
                    client.start();
                    clientCount++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Thread
     */
    public class ClientWorker extends Thread{
        private Logger log = Logger.getLogger(ClientWorker.class);
        Thread currentThread;
        private boolean isConnected = false;
        private Socket socket;
        private int BUFFER_SIZE = 1024;
        private byte[] buffer = new byte[BUFFER_SIZE];
        private String msg = "";
        private int read = 0;
        private InputStream in;
        private OutputStream out;
        public String res;
        public String CR,LF;
        private String tag;
        private int key = 0;
        MessageConsoleCenter center;
        public ClientWorker(Socket socket,int count){
            isConnected = socket.isConnected();
            tag = "Client#" + count;
            log.info(tag + " isConnect.");
            combineReqCommand();
            this.socket = socket;
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                // TODO 自動產生的 catch 區塊
                e.printStackTrace();
            }
        }

        public void setMessageConsoleCenter(MessageConsoleCenter center){
            this.center = center;
        }

        @Override
        public void run(){
            this.currentThread = Thread.currentThread();
            try{
                while ((read = in.read(buffer)) > 0) {
                    if(read!=-1){
                        System.out.println("read -> " + read);
                        msg = new String(buffer,0,read);
//                        System.out.println(tag + " -> " + msg);
                        String [] inline = msg.split("\\|",-1);
                        res = "RES|" + inline[0] + "|KEY|" + key + "|OK" + CR + LF;
                        if(center != null){
                            center.postMessage(res);
                        }
                        send(res.getBytes());
                        key++;
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
        private void combineReqCommand() {
            byte[] bt1 = new byte[1];
            try {
                bt1[0] = 13;
                CR = new String(bt1, 0, 1, "ASCII");
                bt1[0] = 10;
                LF = new String(bt1, 0, 1, "ASCII");
            } catch (UnsupportedEncodingException e) {
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

    public static void main(String args[]){
        new MessageConsoleServer();
    }
}
