package org.jiulou.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jiulou.dao.BaseDAO;
import org.jiulou.dao.GeneralDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.beijing.common.IllegalParameterException;


@Repository("baseDao")
public class BaseDaoImpl extends SqlSessionTemplate implements BaseDAO,GeneralDao {

	@Autowired
	public BaseDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}
	
	@Override
	public <T> int add(String ns, T vo) {
		try {
			BeanUtils.setProperty(vo, "uuid", java.util.UUID.randomUUID().toString());
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		}
		return this.insert(ns + ".add", vo);
	}

	@Override
	public <T> String addAndGetUuid(String ns, T vo) {
		String uuid = java.util.UUID.randomUUID().toString();
		try {
			BeanUtils.setProperty(vo, "uuid", uuid);
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		}
		this.insert(ns + ".add", vo);
		return uuid;
	}
	
	@Override
	public <T> int addBatch(String ns, List<T> lst) {
		if (lst.size() < 1) return 0;
		try {
			for(T vo : lst){
				BeanUtils.setProperty(vo, "uuid", java.util.UUID.randomUUID().toString());
			}
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		}		
		
		return this.insert(ns + ".addBatch", lst);
	}
	
	@Override
	public int updateByKey(String ns, Map<String, Object> map) {
		if(!map.containsKey("id")) return 0;
		return this.update(ns + ".updateByKey", map);
	}

	@Override
	public int deleteByKey(String ns, Map<String, Object> map){
		if(!map.containsKey("id")) return 0;
		return this.delete(ns + ".deleteByKey", map);
	}

	@Override
	public <T> T findByKey(String ns, Map<String, Object> map){
		if(!map.containsKey("id")) return null;
		List<?> lst = this.selectList(ns + ".findByKey",map);
		if (null == lst || 0 == lst.size()) {
			return null;
		}else{
			return (T) lst.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findByUuid(String ns, String uuid) {
		if (null == uuid || uuid.isEmpty()) return null;
		List<?> lst = this.selectList(ns + ".findByUuid",uuid);
		if (null == lst || 0 == lst.size()) {
			return null;
		}else{
			return (T) lst.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> find(String ns, Map<String, Object> map)
			throws Exception {
		List<T> lst = (List<T>) this.selectList(ns + ".find",map);
		return lst;
	}
	
	@Override
	public Long findCount(String ns, Map<String, Object> map)
			throws Exception {
		return (Long) this.selectOne(ns,map);
	}

	@Override
	public <T> Map<String, Object> findByPage(String ns, Map<String, Object> map)
			throws Exception {
		
		Map<String, Object> rst = new HashMap<String, Object>();
		long ttl_cnt  = findCount(ns,map);
		rst.put("ttl_cnt", ttl_cnt);
		
		List<?> voList = this.selectList(ns + ".findByPage",map);
		rst.put("voList", voList);
		rst.put("cnt", voList.size());
		
		return rst;
	}
}
