package com.dang.service;



import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dang.domain.DataTable;
import com.dang.util.HibernateUtil;

public class SaveData {

	public static boolean saveData(DataTable dataTable)
	{
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.openSession();
			transaction = session.beginTransaction();
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.DAY_OF_MONTH,-2);
//			dataTable.setNowdate(cal.getTime());
			dataTable.setNowdate(new Date());
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Query query = session.createQuery("from DataTable where nowdate like '"+dateFormat.format(new Date())+"%'");
			List<DataTable> dataTables = query.list();
			if(dataTables!=null)
			{
				for(DataTable dt:dataTables)
				{
					session.delete(dt);
				}
			}
			session.save(dataTable);
			transaction.commit();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			if(transaction!=null)
			{
				transaction.rollback();
			}
			return false;
		}finally{
			if(session!=null)
			{
				session.close();
			}
		}
	}
}
