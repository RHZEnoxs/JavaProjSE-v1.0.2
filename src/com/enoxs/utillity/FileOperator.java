package com.enoxs.utillity;

import java.io.*;
import java.util.zip.*; 


public class FileOperator {
	public FileOperator(){
//		COMPRESS
//		copyFolder("assets/source","assets/backup");
//		zip_compress("assets/backup");
//		moveFile("assets/backup.zip","assets/external/target/");
//		delFolder("assets/backup");
//		long size = getFileSize("assets/backup.zip");
//		System.out.println("size: " + size);
//		delFile("assets/backup.zip");
	}
	public static long getFileSize(String url) {
        File file = new File(url);
        if (!file.exists() || !file.isFile()) {
            return -1;
        }
        return file.length();
    }
	public void copyFolder(String old_Path, String new_Path) {
		try{
			File dir = new File(new_Path);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
			File source = new File(old_Path);
			String[] file = source.list();
			File temp=null;
			for (int i = 0; i < file.length; i++) {
				if(old_Path.endsWith(File.separator)){
					temp=new File(old_Path+file[i]);
				}
				else{
					temp=new File(old_Path+File.separator+file[i]);
				}
				if(temp.isFile()){
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(new_Path + "/"+
							(temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ( (len = input.read(b)) != -1) {
						output.write(b, 0, len);	
					}
					output.flush();
					output.close();
					input.close();
				}
				if(temp.isDirectory()){
					copyFolder(old_Path+"/"+file[i],new_Path+"/"+file[i]);
				}
			}
			}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void copyFile(String old_Path, String new_Path) {
		File dir = new File(new_Path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
		try {
			File oldfile = new File(old_Path);
			if (oldfile.exists()) { //瑼����
				InputStream input = new FileInputStream(old_Path);//霈������
				FileOutputStream output = new FileOutputStream(new_Path + "/"+
						(oldfile.getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ( (len = input.read(b)) != -1) {
					output.write(b, 0, len);	
				}
				output.flush();
				output.close();
				input.close();				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void delFile(String filePathAndName){
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void delAllFile(String path) {
		File file =new File(path);
		if(!file.exists()) {
			return;
		}
		if(!file.isDirectory()) {
			return;
		}
		String[]tempList = file.list();
		File temp =null;
		for (int i =0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path+"/"+ tempList[i]);
				delFolder(path+"/"+ tempList[i]);
			}
		}
	}
	public void moveFile(String old_Path, String new_Path) {
		copyFile(old_Path, new_Path);
		delFile(old_Path);
	}
	public void zip_compress(String url){
		File dir = new File(url);
		FileInputStream fis;
		File[] files;
		files = dir.listFiles(); 
		int progress_flag = 0;
		try {
			ZipOutputStream zOut = new ZipOutputStream(new BufferedOutputStream(new
			FileOutputStream("assets/"+dir.getName()+".zip")));
			zOut.setLevel(9); 
			if(files.length == 0){ //Empty
				zOut.putNextEntry(new ZipEntry(dir.toString() + "/")); 
				zOut.closeEntry(); 
			}else{
				for(File fileName : files){ 
				System.out.println(progress_flag);
				progress_flag++;
				fis = new FileInputStream(fileName);
				zOut.putNextEntry(new ZipEntry(dir.getName() +"/"+ fileName.getName()));
				int byteNo;
				byte[] b = new byte[64];
				while( (byteNo = fis.read(b)) > 0){
					zOut.write(b,0,byteNo);
				}
				zOut.closeEntry(); 
				fis.close();
				}
			}
				zOut.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public String LoadData(String address){// getFile Content
        FileReader fr;
        StringBuffer sb = new StringBuffer("");
        try {
            fr = new FileReader(address);
            BufferedReader br = new BufferedReader(fr);
            String temp = br.readLine();
            while (temp!=null ){
                sb.append(temp);
                temp=br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void SaveData(String Title,String Content,String path){
        File file1=new File(path);
        if(!file1.exists())
        {
            file1.mkdirs();
        }
        try{
            FileWriter fw = new FileWriter(path + Title, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Content);
            bw.newLine();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
	

	public static void main(String[] args) {
		new FileOperator();
	}

}
