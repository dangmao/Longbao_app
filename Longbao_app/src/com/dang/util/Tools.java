package com.dang.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dang.domain.DataTable;

public class Tools {
	public static String sub(String s1,String s2)
	{
		if(s1==null||s1.equals(""))
		{
			s1 = "0";
		}
		if(s2==null||s2.equals(""))
		{
			s2 = "0";
		}
		if(s1.contains("."))
		{
			int len = s1.length();
			int index1 = s1.indexOf('.');
			int index2 = s2.indexOf('.');
			String news1 = s1.substring(0, index1)+s1.substring(index1+1);
			String news2 = s2.substring(0, index2)+s2.substring(index2+1);
			String result = String.valueOf(Integer.parseInt(news1)-Integer.parseInt(news2));
			 
			if(result.charAt(0)=='-')
			{
				result=result.substring(1);
				int newindex = result.length()-(len-1-index1);
				while(newindex<=0)
				{
					result="0"+result;
					newindex++;
				}
				return "-"+result.substring(0,newindex)+"."+result.substring(newindex);
			}else
			{
				int newindex = result.length()-(len-1-index1);
				while(newindex<=0)
				{
					result="0"+result;
					newindex++;
				}
				return result.substring(0,newindex)+"."+result.substring(newindex);
			}
		}else
		{
			return String.valueOf(Integer.parseInt(s1)-Integer.parseInt(s2));
		}
		
	}
	 public static DataTable getDataIncrement(DataTable today,DataTable yestoday)
	 {
		 DataTable increment = new DataTable();
		 Class clazz = increment.getClass();
		 Field[] fields = clazz.getDeclaredFields();
		 for(Field f:fields)
		 {
			 if(!f.getName().equals("id")&&!f.getName().equals("nowdate"))
			 {
					try {
						Method set = clazz.getMethod(parSetName(f.getName()), String.class);
						Method get = clazz.getMethod(parGetName(f.getName()));
						if(f.getName().endsWith("paiming"))
						{
							//System.out.println(f.getName());
							set.invoke(increment, sub((String)get.invoke(yestoday),(String)get.invoke(today)));	
						}else
						{
							set.invoke(increment, sub((String)get.invoke(today),(String)get.invoke(yestoday)));
						}
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			 }
		 }
		 
		 return increment;
	 }
	 public static DataTable getDataTable(Date date)
	 {
		 	Session session = null;
			Transaction transaction = null;
			try {
				session = HibernateUtil.openSession();
				//transaction = session.beginTransaction();
	
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
				Query query = session.createQuery("from DataTable where nowdate like '"+dateFormat.format(date)+"%'");
				List<DataTable> dataTables = query.list();
				//transaction.commit();
				if(dataTables!=null)
					return dataTables.get(0);
				else
					return null;
			} catch (Exception e) {
				// TODO: handle exception
//				if(transaction!=null||!transaction.wasCommitted())
//				{
//					transaction.rollback();
//				}
				return null;
			}finally{
				if(session!=null)
				{
					session.close();
				}
			}
		 
	 }
	 public static String parGetName(String fieldName) {    

	        return "get"  
	                + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);  
	    }
	 public static String parSetName(String fieldName) {   
	        return "set"  
	                + fieldName.substring(0,1).toUpperCase()+ fieldName.substring(1);  
	    } 

}
