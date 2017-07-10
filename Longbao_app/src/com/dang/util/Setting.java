package com.dang.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.dang.domain.MySetting;

public class Setting {
	public static MySetting readSetting()
	{
		Properties pro = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream("setting.properties");
//			String filePath = Setting.class.getProtectionDomain().getCodeSource().getLocation().getPath(); 
//			int index = filePath.lastIndexOf('/');
//			if(index==-1)
//			{
//				index = filePath.lastIndexOf('\\');
//			}
//			filePath = filePath.substring(0,index+1)+"config/setting.properties"; 
//			in = new FileInputStream(filePath);
			pro.load(in);
			String[] usernames = null;
			String[] passwords = null;
			if(pro.getProperty("username").contains(";"))
			{
				usernames = pro.getProperty("username").split(";");
				passwords = pro.getProperty("password").split(";");
			}
			String path = pro.getProperty("path");
			ArrayList<String> username = new ArrayList<String>();
			ArrayList<String> password = new ArrayList<String>();
			for(int i=0;i<usernames.length;i++)
			{
				username.add(usernames[i]);
				password.add(passwords[i]);
			}
			MySetting ms = new MySetting();
			ms.setUsername(username);
			ms.setPassword(password);
			ms.setPath(path);
			return ms;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			
				try {
					if(in!=null)
						in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	//public static 
	public static void saveSetting(MySetting mySetting)
	{
		String usernames = "";
		String passwords = "";
		for(int i=0;i<mySetting.getUsername().size();i++)
		{
			usernames+=mySetting.getUsername().get(i)+";";
			passwords+=mySetting.getPassword().get(i)+";";
		}
		Properties prop = new Properties();
		FileOutputStream oFile = null;
		try {
			String filePath = Setting.class.getProtectionDomain().getCodeSource().getLocation().getPath(); 
			int index = filePath.lastIndexOf('/');
			if(index==-1)
			{
				index = filePath.lastIndexOf('\\');
			}
			filePath = filePath.substring(0,index+1)+"config/setting.properties"; 
			oFile=new FileOutputStream(filePath);
			//oFile=new FileOutputStream("setting.properties");
			
			prop.setProperty("username", usernames);
			prop.setProperty("password", passwords);
			prop.setProperty("path", mySetting.getPath());
			prop.store(oFile, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					if(oFile!=null)
						oFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
