package com.dang.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class DataGetter 
{
//明星势力榜
	public static HashMap<String,String> getData(String url,String cookie, HashMap<String,String> hm)
	{	
		//System.out.println(cookie);
		String content = getContent(url, cookie);
		content = content.replaceAll("\\s+","");
		//System.out.println(content);
		if(content.equals("")||content==null)
		{
			for(Entry entry:hm.entrySet())
			{
				entry.setValue("0");
			}
		}else{
			for(Entry entry:hm.entrySet())
			{
				String[] regex = ((String) entry.getValue()).split("aaaaa");				
				String temp = regexMatch(content, regex[0]);
				String value = regexMatch(temp, regex[1]);
				entry.setValue(value);	
//				String regex = (String) entry.getValue();
//				String value = regexMatch(content, regex);
//				entry.setValue(value);
			}
			
		}
		
		return hm;
	}	
//贴吧，寻艺，送花
	public static HashMap<String,String> getData(String url, HashMap<String,String> hm)
	{	
		String content = "";
		int pageNum = 1;
		if(url.contains("@"))
		{
			String[] s = getPageContente(url, null);
			pageNum = Integer.parseInt(s[0])-1;
			content = s[1];
		}	
		else{
			content = getContent(url, null);
		}
		content = content.replaceAll("\\s+","");
		//System.out.println(content);
		if(content.equals("")||content==null)
		{
			for(Entry entry:hm.entrySet())
			{
				entry.setValue("0");
			}
		}else{
			for(Entry entry:hm.entrySet())
			{
				String regex = (String) entry.getValue();
				String value = regexMatch(content, regex);
				if(value!="")
					entry.setValue(value);	
				else{
					entry.setValue(Integer.toString(pageNum*15));
				}
			}
			
		}
		
		return hm;
	}
//匹配正则表达式
	private static String regexMatch(String content, String regex) {
		Pattern p = Pattern.compile(regex);
		//System.out.println(regex);
		Matcher m = p.matcher(content);
		String value = "";
		if(m.find())
		{
			String[] s = regex.split("\\.\\+\\?");
			value = m.group().substring(s[0].length(), m.group().length()-s[1].length());
			value = value.replaceAll("<.+?>", "");
		}
		return value;
	}
//得到内容
	private static String getContent(String url, String cookie) {
		HttpClient httpClient = new HttpClient();
		InputStream is = null;
		GetMethod getMethod = new GetMethod(url);	
		if(cookie!=null)
			getMethod.addRequestHeader("Cookie",cookie);
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			System.out.println("response="+statusCode);
			is = getMethod.getResponseBodyAsStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));      
		    StringBuilder sb = new StringBuilder();      
		    String line = null;      
		    while ((line = reader.readLine()) != null) {      
		    	sb.append(line + "\n");      
		    }
		    return sb.toString();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return "";
		}finally {      
            try {
            	if(is!=null)
            		is.close();      
            } catch (IOException e) {      
                e.printStackTrace();      
            }      
        }      

	}

	public static void savePage(String url, String cookie) {
		HttpClient httpClient = new HttpClient();
		InputStream is = null;
		OutputStream os = null;
		GetMethod getMethod = new GetMethod(url);	
		if(cookie!=null)
			getMethod.addRequestHeader("Cookie",cookie);
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			System.out.println("response="+statusCode);
			is = getMethod.getResponseBodyAsStream();
			int tempByte = -1;
			//String FileName = path.substring(path.lastIndexOf('/')+1);
			String FilePath = "D:\\";
			os = new FileOutputStream(FilePath+"a.html");
			while((tempByte=is.read())>0)
			{
				os.write(tempByte);
			}
			System.out.println("ok");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			
		}finally {      
	        try {      
				if(is!=null)
					is.close();
				if(os!=null)
					os.close();      
	        } catch (IOException e) {      
	            e.printStackTrace();      
	        }      
	    }      

	}

	private static String[] getPageContente(String url, String cookie)
	{
		int pageNum = 1;
		String newUrl;
		String content="";
		while(!content.contains("朱一龙")&&!content.contains("\u6731\u4e00\u9f99"))
		{
			newUrl = url.replaceAll("@", Integer.toString(pageNum));
			//System.out.println(newUrl);
			content = getContent(newUrl, cookie);
			pageNum++;
		}
		String[] s= {Integer.toString(pageNum),content};
		return s;
		
		
	}
}





