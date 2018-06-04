package com.enoxs.example.internet;

import com.enoxs.utillity.SettingUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class SocketClient extends Client {
    private static Logger log = Logger.getLogger(SocketClient.class);
    private String res;
    public SocketClient(){
        log.info("init SocketClient.");
        this.start();
    }

    @Override
    public void receiveSocketEvent() {
        try {
            res = getResponseLine();
            log.info("res: " + res);
        } catch (SocketTimeoutException e){
            log.warn("no respose.");
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    public static void main(String args[]){
        new SettingUtils().initConfigSetting();
        new SocketClient();
    }
}
