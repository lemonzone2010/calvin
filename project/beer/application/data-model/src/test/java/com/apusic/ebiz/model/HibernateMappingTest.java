package com.apusic.ebiz.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apusic.ebiz.model.user.Group;
import com.apusic.ebiz.model.user.ResType;
import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.model.user.User;

/**
 * This test case is intended to do smoke test on the mapping.
 * Besides, this test case will isolate the unit test from Oracle by
 * leverage Derby as the database
 * @author apusican
 *
 */
public class HibernateMappingTest {

	private Session session;

	@Before
	public void setup(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
	}

	@Test
	public void groupCurd(){

		//添加根部门
		Group g = new Group();
		g.setName("root");
		g.setDesc("根部门");
		g.setAlias("根");
		g.setDesc(null);
		g.setParent(g);
		session.beginTransaction();
		session.save(g);
		session.getTransaction().commit();

		//添加子节点
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createQuery("from Group where name = ?");
		query.setString(0, "root");
		Group parent = (Group) query.uniqueResult();
		Group child = new Group();
		child.setParent(parent);
		child.setAlias("子群组1");
		child.setDesc("子群组1");
		child.setName("child1");

		Group child2 = new Group();
		child2.setAlias("子群组2");
		child2.setDesc("子群组2");
		child2.setName("child2");
		child2.setParent(parent);

		session.save(child);
		session.save(child2);
		session.getTransaction().commit();

		//删除根部门
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query1 = session.createQuery("from Group where name = ?");
		query1.setString(0, "root");
		Group parent1 = (Group) query1.uniqueResult();
		for(Group p : parent1.getGroups()){
			if(p.getId()!=parent1.getId()){
				session.delete(p);
			}
		}
		//session.delete(parent1);
		session.getTransaction().commit();
	}

	@Test
	public void userAndGroup(){
		/*User user = new User();
		user.setName("xuzhengping");
		user.setPassword("111111");

		User user2 = new User();
		user2.setName("xuzhengping2");
		user2.setPassword("111111");

		session.beginTransaction();
		Query query = session.createQuery("from Group where name = ?");
		query.setString(0, "root");
		Group parent = (Group) query.uniqueResult();
		user.addGroup(parent);
		user2.addGroup(parent);
		session.save(user);
		session.save(user2);

		Group child = new Group();
		child.setParent(parent);
		child.setAlias("子群组1");
		child.setDesc("子群组1");
		child.setName("child1");
		session.save(child);
		user.addGroup(child);
		user2.addGroup(child);
		session.flush();
		session.getTransaction().commit();*/
	}

	@Test
	public void userAndGroupDelete(){
		/*session.beginTransaction()
		User user = (User) session.get(User.class, 75);
		session.delete(user);
		session.getTransaction().commit();*/
	}


	@Test
	public void userAnduserProfileCurd(){
		User user = new User();
		user.setName("admin");
		user.setDisbled(true);
		user.setPassword("111111");
		user.setUserType("user");
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from User where name = ?");
		query.setString(0, "admin");
		User user2 = (User) query.uniqueResult();
		user2.setPassword("111222");
		session.update(user2);
		session.getTransaction().commit();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query2 = session.createQuery("from User where name = ?");
		query2.setString(0, "admin");
		User user3 = (User) query2.uniqueResult();
		session.delete(user3);
		session.getTransaction().commit();
	}

	@Test
	public void userAndRoleCrud(){
		//添加
		User user = new User();
		user.setName("admin");
		user.setDisbled(true);
		user.setPassword("111111");
		user.setUserType("user");
		Role role1 = new Role();
		role1.setName("adminRole");
		role1.setAlias("管理员");
		role1.setDesc("管理员角色");
		Role role2 = new Role();
		role2.setName("userRole");
		role2.setAlias("用户");
		role2.setDesc("用户角色");
		Set<Role> set = new HashSet<Role>();
		set.add(role1);
		set.add(role2);
		user.setRoles(set);
		session.beginTransaction();
		session.save(role1);
		session.save(role2);
		session.flush();
		session.save(user);
		session.getTransaction().commit();

		//删除用户
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from User where name = ?");
		query.setString(0, "admin");
		User user3 = (User) query.uniqueResult();
		session.delete(user3);
		session.getTransaction().commit();

		//删除角色
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query queryRole = session.createQuery("Delete from Role");
		int i = queryRole.executeUpdate();
		session.getTransaction().commit();
	}

	@Test
	public void roleAndResourceCrud(){
		//Open a new session
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Role userRole = new Role();
		userRole.setName("ADMIN");
		userRole.setDesc("管理员");

		Resource resource = new Resource();
		resource.setResType(ResType.URL.toString());
		resource.setResName("/member-center");
		resource.setDesc("会员中心");
		resource.setResContext("member-cneter");

		Set<Resource> resources = new HashSet<Resource>();
		resources.add(resource);
		userRole.setResources(resources);
		session.save(resource);
		session.flush();
		session.save(userRole);
		session.getTransaction().commit();

		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Role where name = ?");
		query.setString(0, "ADMIN");
		Query resourceQuery = session.createQuery("from Resource where resName = ?");
		resourceQuery.setString(0, "/member-center");
		Resource qResource= (Resource) resourceQuery.uniqueResult();
		Role roleResult = (Role) query.uniqueResult();
		//session.delete(roleResult);
		roleResult.setResources(null);
		session.flush();
		session.delete(roleResult);
		session.delete(qResource);
		session.getTransaction().commit();
	}

	@Test
	public void category(){
		/*Category c = new Category();
		c.setName("系统配置");
		c.setIntro("no");
		session.beginTransaction();
		session.save(c);
		session.getTransaction().commit();
		Assert.assertNotSame(0, c.getId());
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ConfigurationData data = new ConfigurationData();
		data.setCategory(c);
		data.setKey("mail.host");
		data.setName("邮件服务器");
		data.setType(DataType.PLAINTEXT);
		data.setValue("mail.apusic.net");
		session.save(data);
		session.getTransaction().commit();
		Assert.assertNotSame(0, data.getId());
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Category c2 = (Category) session.get(Category.class, 1);
		Assert.assertNotNull(c2);*/
	}

	@After
	public void teardown(){
		if(session!=null){
			try{
				session.close();
			}catch (Exception e) {
			}
		}
	}
}
