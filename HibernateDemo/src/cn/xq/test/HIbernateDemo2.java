package cn.xq.test;


import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.tools.javac.code.Attribute.Array;


import sun.tools.tree.ArrayAccessExpression;

import cn.xq.domain.Customer;
import cn.xq.domain.LinkMan;
import cn.xq.domain.Role;
import cn.xq.domain.User;

public class HIbernateDemo2 {
	private Session session= null;

	@Test
	public void testDemo()
	{
		queryInner();
	}
	
	//HQL 内连接查询
	public  void queryInner(){
		// 使用内连接查询出来的数据各自封装到自己的对象，使用fetch查询出来的数据封装到一个对象当中去，使用distinct去重
		// 开启事务
		Transaction tx = session.beginTransaction();
		tx.begin();
		List list = session.createQuery("select distinct c from Customer c inner join fetch c.linkMans").list();
		System.out.println(list);
		tx.commit();
	}
	
	/**
	 * 针对多对多的关系
	 */
	public void ManyToMany()
	{
		// 开启事务
		Transaction tx = session.beginTransaction();
		tx.begin();
		User user1 = new User();
		user1.setUser_name("张三");
		User user2 = new User();
		user2.setUser_name("李四");
		
		Role role1 = new Role();
		role1.setRole_name("JAVA工程师");
		Role role2 = new Role();
		role2.setRole_name("IOS工程师");
		
		user1.getRoles().add(role1);
		user1.getRoles().add(role2);
		
		user2.getRoles().add(role1);
		user2.getRoles().add(role2);

		
		role1.getUsers().add(user1);
		role1.getUsers().add(user2);
		role2.getUsers().add(user1);
		role2.getUsers().add(user2);

		session.save(user1);
		session.save(user2);
		//session.save(role1);
		//session.save(role2);
		tx.commit();
		
	}
	
	/**
	 * 针对一对多的关系
	 * 没有设置级联关系进行数据插入操作
	 */
	public void saveManyPerple(){
		// 开启事务
		Transaction tx = session.beginTransaction();
		tx.begin();
		Customer customer = new Customer();
		customer.setCust_name("小沈阳");
		customer.setCust_user_id(107L);
		customer.setCust_create_id(107L);
		customer.setCust_source("猎聘1");
		customer.setCust_industry("H5开发");
		customer.setCust_level("Vip4");
		customer.setCust_linkman("习大大2");
		customer.setCust_phone("028-9876323");
		customer.setCust_mobile("18784406533");
		
		// 创建两个联系人
		LinkMan linkMan1 = new LinkMan();
		linkMan1.setLkm_name("奥爆吗");
		LinkMan linkMan2 = new LinkMan();
		linkMan2.setLkm_name("御书籍");
		
		// 建立关系
		customer.getLinkMans().add(linkMan1);
		customer.getLinkMans().add(linkMan2);
		linkMan1.setCustomer(customer);
		linkMan2.setCustomer(customer);
		session.save(customer);
		//session.save(linkMan1);
		//session.save(linkMan2);
		tx.commit();
		
	} 
	@Before
	public void loadConfiguratin()
	{
		// 1: 加载配置文件
		Configuration cfg = new Configuration().configure();
		// 2: 创建一个SessionFactory
		SessionFactory  sessionFactory = cfg.buildSessionFactory();
		// 3: 创建Session对象类似Connection
		session = sessionFactory.openSession();
	}
	@After
	public void closeSession()
	{
		// 关闭session 释放资源
		if(session != null){
			session.close();
		}
	}
}
