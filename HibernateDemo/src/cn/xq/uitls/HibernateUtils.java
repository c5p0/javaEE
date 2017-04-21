package cn.xq.uitls;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {
	
	private static final  Configuration config;
	private static final  SessionFactory sessionFactory;
	
	// 保证代码只执行一次
	static{
		config = new Configuration().configure();
		sessionFactory = config.buildSessionFactory();	
	}
	
	public static Session openSession(){
		return sessionFactory.getCurrentSession();
	}
}
