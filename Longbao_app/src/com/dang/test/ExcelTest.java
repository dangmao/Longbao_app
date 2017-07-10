package com.dang.test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.dang.domain.*;
import com.dang.service.ExportExcel;
import com.dang.service.GetData;
import com.dang.service.SettingService;
import com.dang.util.Setting;

public class ExcelTest {

	public static void main(String[] args) {
		//GetData.getData(Setting.readSetting());
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_MONTH,+2);
//		int dayInWeek = cal.get(Calendar.DAY_OF_WEEK);
//		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(dateFormat.format(cal.getTime()));
//		System.out.println(dayInWeek);
		ExportExcel.export("D:\\");
	}

}
