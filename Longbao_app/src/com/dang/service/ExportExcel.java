package com.dang.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.List;

import   org.apache.poi.hssf.usermodel.HSSFCell;   
import   org.apache.poi.hssf.usermodel.HSSFCellStyle;   
import   org.apache.poi.hssf.usermodel.HSSFRow;   
import   org.apache.poi.hssf.usermodel.HSSFSheet;   
import   org.apache.poi.hssf.usermodel.HSSFWorkbook;   
import   org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dang.domain.DataTable;
import com.dang.util.HibernateUtil;
import com.dang.util.Tools; 

public class ExportExcel {
	 public static boolean export(String path)
	 {
		 Calendar cal = Calendar.getInstance();
		 java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//取出今天的
		// cal.add(Calendar.DAY_OF_MONTH,-1);
		DataTable today = Tools.getDataTable(cal.getTime());
		int dayinweek_t = cal.get(Calendar.DAY_OF_WEEK);
		//取出昨天的
		
		cal.add(Calendar.DAY_OF_MONTH,-1);
		int dayinweek_y = cal.get(Calendar.DAY_OF_WEEK);
		DataTable yestoday = Tools.getDataTable(cal.getTime());
		//前天
		cal.add(Calendar.DAY_OF_MONTH,-1);
		DataTable beforeY = Tools.getDataTable(cal.getTime());
		//改变量的
		//今天周一
		DataTable y2t = null;
		DataTable b2y = null;
		if(dayinweek_t==2)
		{
			b2y = Tools.getDataIncrement(yestoday, beforeY);
			yestoday.setBaidu_songhua("0");
			yestoday.setMingxing_aimu("0");
			yestoday.setMingxing_hudong("0");
			yestoday.setMingxing_sousuo("0");
			yestoday.setMingxing_tiji("0");
			y2t = Tools.getDataIncrement(today, yestoday);
			cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH,-1);
			dayinweek_y = cal.get(Calendar.DAY_OF_WEEK);
			yestoday = Tools.getDataTable(cal.getTime());
		}else if(dayinweek_y==2)
		{
			y2t = Tools.getDataIncrement(today, yestoday);
			beforeY.setBaidu_songhua("0");
			beforeY.setMingxing_aimu("0");
			beforeY.setMingxing_hudong("0");
			beforeY.setMingxing_sousuo("0");
			beforeY.setMingxing_tiji("0");
			b2y = Tools.getDataIncrement(yestoday, beforeY);
			
		}else
		{
			y2t = Tools.getDataIncrement(today, yestoday);
			b2y = Tools.getDataIncrement(yestoday, beforeY);
			
		}
		DataTable increment = Tools.getDataIncrement(y2t, b2y);
			
			
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle cellStyle=wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		 
		HSSFSheet sheet = wb.createSheet("数据表");
		HSSFRow[] rows = new HSSFRow[14];
		HSSFCell[][] cell = new HSSFCell[14][9];
		//sheet.autoSizeColumn(0);
		
		rows[0] = sheet.createRow(0);
		cell[0][0]=rows[0].createCell(0);
		cell[0][0].setCellValue(dateFormat.format(Calendar.getInstance().getTime())+"数据总结（截至23:00）");
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
		
		rows[1]=sheet.createRow(1); 
		cell[1][0]=rows[1].createCell(0);
		cell[1][0].setCellValue("项目");
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
		cell[1][2]=rows[1].createCell(2);
		cell[1][2].setCellValue("昨日数据");
		cell[1][3]=rows[1].createCell(3);
		cell[1][3].setCellValue("昨日净增加");
		cell[1][4]=rows[1].createCell(4);
		cell[1][4].setCellValue("今日数据");
		cell[1][5]=rows[1].createCell(5);
		cell[1][5].setCellValue("今日净增加");
		cell[1][6]=rows[1].createCell(6);
		cell[1][6].setCellValue("增加／减少");
		cell[1][7]=rows[1].createCell(7);
		cell[1][7].setCellValue("排名");
		cell[1][8]=rows[1].createCell(8);
		cell[1][8].setCellValue("增加／减少");
		
		rows[2]=sheet.createRow(2); 
		cell[2][0]=rows[2].createCell(0);
		cell[2][0].setCellValue("超级话题");
		sheet.addMergedRegion(new CellRangeAddress(2,3,0,0));
		cell[2][1]=rows[2].createCell(1);
		cell[2][1].setCellValue("签到数");
		cell[2][2]=rows[2].createCell(2);
		cell[2][2].setCellValue(yestoday.getChaohua_qiandao());
		cell[2][3]=rows[2].createCell(3);
		cell[2][3].setCellValue("/");
		cell[2][4]=rows[2].createCell(4);
		cell[2][4].setCellValue(today.getChaohua_qiandao());
		cell[2][5]=rows[2].createCell(5);
		cell[2][5].setCellValue("/");
		cell[2][6]=rows[2].createCell(6);
		cell[2][6].setCellValue(y2t.getChaohua_qiandao());
		cell[2][7]=rows[2].createCell(7);
		cell[2][7].setCellValue(today.getChaohua_paiming());
		sheet.addMergedRegion(new CellRangeAddress(2,3,7,7));
		cell[2][8]=rows[2].createCell(8);
		cell[2][8].setCellValue(y2t.getChaohua_paiming());
		sheet.addMergedRegion(new CellRangeAddress(2,3,8,8));
		
		rows[3] = sheet.createRow(3); 
		cell[3][1]=rows[3].createCell(1);
		cell[3][1].setCellValue("帖子量");
		cell[3][2]=rows[3].createCell(2);
		cell[3][2].setCellValue(yestoday.getChaohua_tiezi());
		cell[3][3]=rows[3].createCell(3);
		cell[3][3].setCellValue(b2y.getChaohua_tiezi());
		cell[3][4]=rows[3].createCell(4);
		cell[3][4].setCellValue(today.getChaohua_tiezi());
		cell[3][5]=rows[3].createCell(5);
		cell[3][5].setCellValue(y2t.getChaohua_tiezi());
		cell[3][6]=rows[3].createCell(6);
		cell[3][6].setCellValue(increment.getChaohua_tiezi());
		
		rows[4] = sheet.createRow(4); 
		cell[4][0]=rows[4].createCell(0);
		cell[4][0].setCellValue("明星势力榜");
		sheet.addMergedRegion(new CellRangeAddress(4,8,0,0));
		cell[4][1]=rows[4].createCell(1);
		cell[4][1].setCellValue("提及量");
		cell[4][2]=rows[4].createCell(2);
		cell[4][2].setCellValue(yestoday.getMingxing_tiji());
		cell[4][3]=rows[4].createCell(3);
		cell[4][3].setCellValue(b2y.getMingxing_tiji());
		cell[4][4]=rows[4].createCell(4);
		cell[4][4].setCellValue(today.getMingxing_tiji());
		cell[4][5]=rows[4].createCell(5);
		cell[4][5].setCellValue(y2t.getMingxing_tiji());
		cell[4][6]=rows[4].createCell(6);
		cell[4][6].setCellValue(increment.getMingxing_tiji());
		cell[4][7]=rows[4].createCell(7);
		cell[4][7].setCellValue(today.getMingxing_tiji_paiming());
		cell[4][8]=rows[4].createCell(8);
		cell[4][8].setCellValue(y2t.getMingxing_tiji_paiming());

		rows[5] = sheet.createRow(5); 
		cell[5][1]=rows[5].createCell(1);
		cell[5][1].setCellValue("互动量");
		cell[5][2]=rows[5].createCell(2);
		cell[5][2].setCellValue(yestoday.getMingxing_hudong());
		cell[5][3]=rows[5].createCell(3);
		cell[5][3].setCellValue(b2y.getMingxing_hudong());
		cell[5][4]=rows[5].createCell(4);
		cell[5][4].setCellValue(today.getMingxing_hudong());
		cell[5][5]=rows[5].createCell(5);
		cell[5][5].setCellValue(y2t.getMingxing_hudong());
		cell[5][6]=rows[5].createCell(6);
		cell[5][6].setCellValue(increment.getMingxing_hudong());
		cell[5][7]=rows[5].createCell(7);
		cell[5][7].setCellValue(today.getMingxing_hudong_paiming());
		cell[5][8]=rows[5].createCell(8);
		cell[5][8].setCellValue(y2t.getMingxing_hudong_paiming());
		
		rows[6] = sheet.createRow(6); 
		cell[6][1]=rows[6].createCell(1);
		cell[6][1].setCellValue("搜索量");
		cell[6][2]=rows[6].createCell(2);
		cell[6][2].setCellValue(yestoday.getMingxing_sousuo());
		cell[6][3]=rows[6].createCell(3);
		cell[6][3].setCellValue(b2y.getMingxing_sousuo());
		cell[6][4]=rows[6].createCell(4);
		cell[6][4].setCellValue(today.getMingxing_sousuo());
		cell[6][5]=rows[6].createCell(5);
		cell[6][5].setCellValue(y2t.getMingxing_sousuo());
		cell[6][6]=rows[6].createCell(6);
		cell[6][6].setCellValue(increment.getMingxing_sousuo());
		cell[6][7]=rows[6].createCell(7);
		cell[6][7].setCellValue(today.getMingxing_sousuo_paiming());
		cell[6][8]=rows[6].createCell(8);
		cell[6][8].setCellValue(y2t.getMingxing_sousuo_paiming());
		
		rows[7] = sheet.createRow(7); 
		cell[7][1]=rows[7].createCell(1);
		cell[7][1].setCellValue("爱慕值");
		cell[7][2]=rows[7].createCell(2);
		cell[7][2].setCellValue(yestoday.getMingxing_aimu());
		cell[7][3]=rows[7].createCell(3);
		cell[7][3].setCellValue(b2y.getMingxing_aimu());
		cell[7][4]=rows[7].createCell(4);
		cell[7][4].setCellValue(today.getMingxing_aimu());
		cell[7][5]=rows[7].createCell(5);
		cell[7][5].setCellValue(y2t.getMingxing_aimu());
		cell[7][6]=rows[7].createCell(6);
		cell[7][6].setCellValue(increment.getMingxing_aimu());
		cell[7][7]=rows[7].createCell(7);
		cell[7][7].setCellValue(today.getMingxing_aimu_paiming());
		cell[7][8]=rows[7].createCell(8);
		cell[7][8].setCellValue(y2t.getMingxing_aimu_paiming());
		
		rows[8] = sheet.createRow(8); 
		cell[8][1]=rows[8].createCell(1);
		cell[8][1].setCellValue("总分");
		cell[8][2]=rows[8].createCell(2);
		cell[8][2].setCellValue(yestoday.getMingxing_zongfen());
		cell[8][3]=rows[8].createCell(3);
		cell[8][3].setCellValue("/");
		cell[8][4]=rows[8].createCell(4);
		cell[8][4].setCellValue(today.getMingxing_zongfen());
		cell[8][5]=rows[8].createCell(5);
		cell[8][5].setCellValue("/");
		cell[8][6]=rows[8].createCell(6);
		cell[8][6].setCellValue(y2t.getMingxing_zongfen());
		cell[8][7]=rows[8].createCell(7);
		cell[8][7].setCellValue(today.getMingxing_zongfen_paiming());
		cell[8][8]=rows[8].createCell(8);
		cell[8][8].setCellValue(y2t.getMingxing_zongfen_paiming());
		
		rows[9]=sheet.createRow(9); 
		cell[9][0]=rows[9].createCell(0);
		cell[9][0].setCellValue("百度贴吧");
		sheet.addMergedRegion(new CellRangeAddress(9,10,0,0));
		cell[9][1]=rows[9].createCell(1);
		cell[9][1].setCellValue("签到数");
		cell[9][2]=rows[9].createCell(2);
		cell[9][2].setCellValue(yestoday.getTieba_qiandao());
		cell[9][3]=rows[9].createCell(3);
		cell[9][3].setCellValue("/");
		cell[9][4]=rows[9].createCell(4);
		cell[9][4].setCellValue(today.getTieba_qiandao());
		cell[9][5]=rows[9].createCell(5);
		cell[9][5].setCellValue("/");
		cell[9][6]=rows[9].createCell(6);
		cell[9][6].setCellValue(y2t.getTieba_qiandao());
		cell[9][7]=rows[9].createCell(7);
		cell[9][7].setCellValue(today.getTieba_paiming());
		sheet.addMergedRegion(new CellRangeAddress(9,10,7,7));
		cell[9][8]=rows[9].createCell(8);
		cell[9][8].setCellValue(y2t.getTieba_paiming());
		sheet.addMergedRegion(new CellRangeAddress(9,10,8,8));
		
		rows[10] = sheet.createRow(10); 
		cell[10][1]=rows[10].createCell(1);
		cell[10][1].setCellValue("帖子量");
		cell[10][2]=rows[10].createCell(2);
		cell[10][2].setCellValue(yestoday.getTieba_tiezi());
		cell[10][3]=rows[10].createCell(3);
		cell[10][3].setCellValue(b2y.getTieba_tiezi());
		cell[10][4]=rows[10].createCell(4);
		cell[10][4].setCellValue(today.getTieba_tiezi());
		cell[10][5]=rows[10].createCell(5);
		cell[10][5].setCellValue(y2t.getTieba_tiezi());
		cell[10][6]=rows[10].createCell(6);
		cell[10][6].setCellValue(increment.getTieba_tiezi());
		
		rows[11] = sheet.createRow(11); 
		cell[11][0]=rows[11].createCell(0);
		cell[11][0].setCellValue("百度送花");
		cell[11][1]=rows[11].createCell(1);
		cell[11][1].setCellValue("鲜花数");
		cell[11][2]=rows[11].createCell(2);
		cell[11][2].setCellValue(yestoday.getBaidu_songhua());
		cell[11][3]=rows[11].createCell(3);
		cell[11][3].setCellValue(b2y.getBaidu_songhua());
		cell[11][4]=rows[11].createCell(4);
		cell[11][4].setCellValue(today.getBaidu_songhua());
		cell[11][5]=rows[11].createCell(5);
		cell[11][5].setCellValue(y2t.getBaidu_songhua());
		cell[11][6]=rows[11].createCell(6);
		cell[11][6].setCellValue(increment.getBaidu_songhua());
		cell[11][7]=rows[11].createCell(7);
		cell[11][7].setCellValue(today.getBaidu_songhua_paiming());
		cell[11][8]=rows[11].createCell(8);
		cell[11][8].setCellValue(y2t.getBaidu_songhua_paiming());
		
		rows[12] = sheet.createRow(12); 
		cell[12][0]=rows[12].createCell(0);
		cell[12][0].setCellValue("V榜");
		sheet.addMergedRegion(new CellRangeAddress(12,13,0,0));
		cell[12][1]=rows[12].createCell(1);
		cell[12][1].setCellValue("新媒体指数");
		cell[12][2]=rows[12].createCell(2);
		cell[12][2].setCellValue(yestoday.getVbang_zhishu());
		cell[12][3]=rows[12].createCell(3);
		cell[12][3].setCellValue("/");
		cell[12][4]=rows[12].createCell(4);
		cell[12][4].setCellValue(today.getVbang_zhishu());
		cell[12][5]=rows[12].createCell(5);
		cell[12][5].setCellValue("/");
		cell[12][6]=rows[12].createCell(6);
		cell[12][6].setCellValue(y2t.getVbang_zhishu());
		cell[12][7]=rows[12].createCell(7);
		cell[12][7].setCellValue(today.getVbang_paiming());
		cell[12][8]=rows[12].createCell(8);
		cell[12][8].setCellValue(y2t.getVbang_paiming());

		rows[13] = sheet.createRow(13); 
		cell[13][1]=rows[13].createCell(1);
		cell[13][1].setCellValue("寻艺签到数");
		cell[13][2]=rows[13].createCell(2);
		cell[13][2].setCellValue(yestoday.getXunyi_qiandao());
		cell[13][3]=rows[13].createCell(3);
		cell[13][3].setCellValue("/");
		cell[13][4]=rows[13].createCell(4);
		cell[13][4].setCellValue(today.getXunyi_qiandao());
		cell[13][5]=rows[13].createCell(5);
		cell[13][5].setCellValue("/");
		cell[13][6]=rows[13].createCell(6);
		cell[13][6].setCellValue(y2t.getXunyi_qiandao());
		cell[13][7]=rows[13].createCell(7);
		cell[13][7].setCellValue(today.getXunyi_paiming());
		cell[13][8]=rows[13].createCell(8);
		cell[13][8].setCellValue(y2t.getXunyi_paiming());
		
		for(int i=0;i<9;i++)
		{
			sheet.setColumnWidth(i,3000);
			for(int j=0;j<14;j++)
			{
				if(cell[j][i]!=null)
					cell[j][i].setCellStyle(cellStyle);
			}
		}
		for(int j=0;j<14;j++)
		{
			rows[j].setHeight((short) 400);
		}
		
		
		FileOutputStream output=null;
		try {
			if(!path.endsWith("\\"))
			{
				path+="\\";
			
			}
			output = new FileOutputStream(path+dateFormat.format(Calendar.getInstance().getTime())+".xls");
			wb.write(output);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			
				try {
					if(output!=null)
						output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		
		
		
	 }

}
