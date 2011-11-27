package org.jiulou.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.jiulou.vo.User;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.Transactional;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;
import org.unitils.database.util.TransactionMode;

@SpringApplicationContext({"classpath:spring/applicationContext-common.xml", "classpath:spring/applicationContext-security.xml"})
public class UserDaoTest extends UnitilsJUnit4{
	
	private static final String ORG_JIULOU_DAO_USER = "org.jiulou.dao.user";

	@SpringBeanByName
	private BaseDAO baseDao;
	
	private static List<User> testUserLst = new ArrayList<User>();
	static {
		for (int i = 0; i < 10; i ++){
			User vo = new User();
			String user = "user" + i;
			vo.setEmail(user + "@gmail.com");
			vo.setUsername(user);
			testUserLst.add(vo);
		}
		
		User vo = new User();
		vo.setEmail("208431@gmail.com1");
		vo.setUsername("Singchen1");
		//vo.setUsername()
		vo.setId(-1L);
		
		testUserLst.add(vo);
	}
	
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void insertTest(){
		User vo = testUserLst.get(testUserLst.size() - 1);
		int cnt = baseDao.add(ORG_JIULOU_DAO_USER, vo);
		Assert.assertTrue("没有插入成功",1 == cnt);
	}
	
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void addAndGetUuidTest(){
		User vo = testUserLst.get(testUserLst.size() - 1);
		String uuid = baseDao.addAndGetUuid(ORG_JIULOU_DAO_USER, vo);
		Assert.assertTrue("没有插入成功",!uuid.equals(""));
	}
	
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void insertBatchTest(){
		List<User> lst = new ArrayList<User>();
		int cnt = baseDao.addBatch(ORG_JIULOU_DAO_USER, lst);
		Assert.assertTrue(cnt < 1);
		
		cnt = baseDao.addBatch(ORG_JIULOU_DAO_USER, testUserLst);
		Assert.assertTrue(cnt == testUserLst.size());
	}
	
	@Test	
	public void findByUuidTest1(){
		
		// can't find
		
		String uuid = "";
		User vo = baseDao.findByUuid(ORG_JIULOU_DAO_USER, uuid);
		Assert.assertNull(vo);
		
		uuid = null;
		vo = baseDao.findByUuid(ORG_JIULOU_DAO_USER, uuid);
		Assert.assertNull(vo);
	}
	
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void findByUuidTest2(){
		
		// can find
		
		User vo = testUserLst.get(testUserLst.size() - 1);
		String uuid = baseDao.addAndGetUuid(ORG_JIULOU_DAO_USER, vo);
		User vo2 = baseDao.findByUuid(ORG_JIULOU_DAO_USER, uuid);
		
		Assert.assertEquals(uuid, vo2.getUuid());
	}
	
	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void findByKeyTest(){
		
		User vo1 = testUserLst.get(testUserLst.size() - 1);
		String uuid = baseDao.addAndGetUuid(ORG_JIULOU_DAO_USER, vo1);
		User vo2 = baseDao.findByUuid(ORG_JIULOU_DAO_USER, uuid);
		
		Map<String,Object> map = new HashMap<String,Object>();
		User vo3 = baseDao.findByKey(ORG_JIULOU_DAO_USER, map);
		
		Assert.assertTrue("应该返回null",null == vo3);
		
		map.put("id", vo2.getId());
		vo3 = baseDao.findByKey(ORG_JIULOU_DAO_USER, map);
		
		Assert.assertTrue("两个对象属性相同", vo2.getUsername().equals(vo3.getUsername()));
		
	}
	
	@Test
	public void findTest() throws Exception{
		Map<String, Object> map = new HashMap<String,Object>();
		List<User> lst = this.baseDao.find(ORG_JIULOU_DAO_USER, map );
		for(User vo:lst){
			vo.getEmail();
		}
	}
}
