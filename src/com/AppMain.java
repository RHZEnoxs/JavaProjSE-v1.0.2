package com;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AppMain {
	private static Logger log = Logger.getLogger(AppMain.class);
	public AppMain() {
		try {
			loadConfigProps();
			loadLog4jProps();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("Hello , Java Developer.");
	}

	public static void main(String[] args) {
		new AppMain();
	}
	
	private void loadLog4jProps() {
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
	private void loadConfigProps() {
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
