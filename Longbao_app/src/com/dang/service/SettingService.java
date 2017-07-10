package com.dang.service;

import com.dang.domain.MySetting;


public class SettingService {

	public static void addWeibo(String name,String pass,MySetting mySetting)
	{
		mySetting.getUsername().add(name);
		mySetting.getPassword().add(pass);
	}
	public static void removeWeibo(int num,MySetting mySetting)
	{
		if(num!=0)
		{
			mySetting.getUsername().remove(num);
			mySetting.getPassword().remove(num);
		}
		
	}
}
