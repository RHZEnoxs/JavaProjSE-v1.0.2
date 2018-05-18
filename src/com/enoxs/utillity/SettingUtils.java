package com.enoxs.utillity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class SettingUtils {
	private static Logger log = Logger.getLogger(SettingUtils.class);
	
	public SettingUtils() {
		// TODO 自動產生的建構子 Stub
	}
	public void initConfigSetting(){
		initLog4j();
		initConfig();
	}
	public Properties getProperties(String adress){
		Properties readHisPara = null;
		try {
			readHisPara = new Properties();
			FileInputStream fin = new FileInputStream(adress);
			readHisPara.load(fin);
			fin.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return readHisPara;
	}
	
	public void initLog4j() {
		Properties prob = new Properties();
		try {
        	FileInputStream fis = new FileInputStream("config/log4j.properties");
            prob.load(fis);
            fis.close();
            fis = null;
            
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
		PropertyConfigurator.configure(prob);
	}
	public  void initConfig() {
		try {
            FileInputStream fis = new FileInputStream("config/config.properties");
            Properties props = new Properties();
            props.load(fis);
            System.out.println("Project : " + props.getProperty("app_name"));
            System.out.println("Version : " + props.getProperty("app_version"));
            System.out.println("Data : " + props.getProperty("app_data"));
            System.out.println("Author : " + props.getProperty("app_author"));
            System.out.println("Remark : " + props.getProperty("app_remark"));
            fis.close();
            fis = null;
            
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
	}

}
