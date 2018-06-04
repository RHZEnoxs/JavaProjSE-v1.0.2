package com.enoxs.example.internet;

import com.enoxs.utillity.SettingUtils;
import org.apache.log4j.Logger;

import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.Scanner;

public class SocketHello extends Client {
    private static Logger log = Logger.getLogger(SocketMonitor.class);
    private SettingUtils setup = new SettingUtils();
    public SocketHello(){
        init();
        ctrl();
    }

    int timeout;
    public void init(){
        setup.initLog4j();
        Properties props = setup.getProperties("config/monitor.properties");
        System.out.println("Project : " + props.getProperty("app_name"));
        System.out.println("Version : " + props.getProperty("app_version"));
        System.out.println("Data : " + props.getProperty("app_data"));
        System.out.println("Author : " + props.getProperty("app_author"));
        System.out.println("Remark : " + props.getProperty("app_remark"));
        String ip = props.getProperty("app_monitor_ip");
        String port = props.getProperty("app_monitor_port");
        timeout = Integer.parseInt(props.getProperty("app_monitor_timeout"));
        System.out.println("IP address : " + ip + ":" + port);
        System.out.println("TIMEOUT : " + timeout);
        super.setRemoteAddress(ip,Integer.parseInt(port));
    }
    public void ctrl(){
        System.out.println("\n ");
        System.out.println("1. start");
        System.out.println("2. stop");
        System.out.println("\n -- --- --- --- --- --- ---");
        System.out.println("0. exit");

        Scanner input = new Scanner(System.in);
        String ctrl;
        while(true){
            ctrl = input.next();
            switch (ctrl){
                case "1":
                    super.isRunning = true;
                    super.start();
                    break;
                case "2":
                    super.isRunning = false;
                    System.out.println("Stop Socket Monitor Thread.");
                    break;
                case "3":
                    super.isRunning = true;
                    super.connected = false;
                    super.connecting = false;
                    this.run();
                    break;
                case "5":
                    String msg = "Hello\r\n";
                    super.sendCommand(msg);
                    break;
                case "0":
                case "exit":
                    System.exit(0);
                    break;
            }
        }
    }
    @Override
    public void run(){
        int read = 0;
        StringBuffer sb = new StringBuffer(2048);
        char[] buffer = new char[2048];
        while (isRunning) {
            try {
                if (connected) {
                    while ((read = br.read(buffer)) > 0) {
                        if(read!=-1){
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
                            System.out.println(read + " : " + sb.toString());
                            Thread.sleep(100);
                        }
                        Thread.sleep(500);
                    }
                    isRunning = false;
                    stopRun();
                } else {
                    log.info("try to connect Device");
                    connectDevice();
                    setSocketTimeOut(timeout);
                }
            } catch (SocketTimeoutException e){
                log.warn("no response.");
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
            } catch(Exception e){
                log.error(e.getMessage(),e);
            }
        }
    }

    @Override
    public void receiveSocketEvent() {

    }


    public static void main(String[] args) {
        new SocketHello();
    }
}
