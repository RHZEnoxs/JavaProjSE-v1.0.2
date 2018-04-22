package com.enoxs.utillity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ConfigUtils {
	private static Logger log = Logger.getLogger(ConfigUtils.class);
	
	public ConfigUtils() {
		// TODO 自動產生的建構子 Stub
	}
	public void initConfigSetting(){
		loadLog4jProps();
		loadConfigProps();
	}
	public Properties getProperties() throws IOException {
		Properties readHisPara = new Properties();
		FileInputStream fin = new FileInputStream("./connDB.ini");
		readHisPara.load(fin);
		fin.close();
		return readHisPara;
	}
	
	public void loadLog4jProps() {
		Properties prob = new Properties();
		try {
        	FileInputStream fis = new FileInputStream("src/res/config/log4j.properties");
            prob.load(fis);
            fis.close();
            fis = null;
            
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
		PropertyConfigurator.configure(prob);
	}
	public  void loadConfigProps() {
		try {
            FileInputStream fis = new FileInputStream("src/res/config/config.properties");
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
