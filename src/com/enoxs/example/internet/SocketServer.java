package com.enoxs.example.internet;

import com.enoxs.utillity.SettingUtils;
import org.apache.log4j.Logger;

public class SocketServer extends Server{
    private static Logger log = Logger.getLogger(SocketServer.class);
    public SocketServer(){
        log.info("init SocketServer.");
        this.start();
    }
    public static void main(String args[]){
        new SettingUtils().initConfigSetting();
        new SocketServer();
    }
}
