package cn.xq.test;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import cn.xq.domain.Customer;

/**
 * 针对hibernate 单表进行Test
 * @author shijiyangtian
 *
 */
public class HibernateDemo1 {

	private Session session= null;
	
	@Test
	public void testDemo()
	{
		qBCList();
	}
	/**
	 * 插入顾客表数据
	 */
	public void insertCustomer()
	{
		// 开启事务
		Transaction tx = session.beginTransaction();
		tx.begin();
		Customer customer = new Customer();
		customer.setCust_name("刘能");
		customer.setCust_user_id(106L);
		customer.setCust_create_id(106L);
		customer.setCust_source("拉勾网11");
		customer.setCust_industry("web开发1");
		customer.setCust_level("Vip3");
		customer.setCust_linkman("习大大");
		customer.setCust_phone("028-9876323");
		customer.setCust_mobile("18784406533");
		session.save(customer);
	}
	/**
	 * 删除用户
	 */
	public void deleteCustomer()
	{
		Transaction tx = session.beginTransaction();
		tx.begin();
		Customer customer = session.get(Customer.class, 8L);
		session.delete(customer);
		tx.commit();
	}
	/**
	 * 根据ID获取单条数据
	 */
	public  void getCustomer(){
		Transaction tx = session.beginTransaction();
		tx.begin();
		Customer customer = session.get(Customer.class, 4L);
		customer.setCust_name("马云");
		tx.commit();
		System.out.println(customer);
	}
	/**
	 * 根据HQL查询语句进行查询数据列表
	 */
	public void queryList()
	{
		// 1: 查询所有记录
		//Query query = session.createQuery("from Customer");
		// 2: 根据条件查询
		//Query query = session.createQuery("from Customer where cust_name = ?");
		//query.setString(0, "李四");
		// 3: 条件查询3
		//Query query = session.createQuery("from Customer where cust_name = :custName");
		//query.setString("custName", "李四");
		// 4: 分页查询
		Query query = session.createQuery("from Customer");
	    query.setFirstResult(3); // 从第几条开始查询起
		query.setMaxResults(2);  // 一次查询几条数据
		List<Customer> list =  query.list();
		System.out.println(list);
	}
	public void qBCList()
	{
		// 1: 查询所有记录
		Criteria criteria = session.createCriteria(Customer.class);
		// 2: 条件查询
		criteria.add(Restrictions.eq("cust_name","马云"));
		List<Customer> list =  criteria.list();
		System.out.println(list);
		
	}
	
	/**
	 * 测试session一级缓存
	 */
	public  void testSessionCache()
	{
		Transaction tx = session.beginTransaction();
		tx.begin();
		Customer customer = session.get(Customer.class, 4L);
		Customer customer2 = session.get(Customer.class, 4L);
		System.out.println(customer== customer2);
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
