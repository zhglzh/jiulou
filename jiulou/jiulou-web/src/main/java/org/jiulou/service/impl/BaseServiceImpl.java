package org.jiulou.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.jiulou.dao.BaseDAO;
import org.jiulou.service.BaseService;
import org.springframework.stereotype.Service;

import com.beijing.common.BusinessException;

@Service("baseService")
public class BaseServiceImpl implements BaseService{
	
	@Resource
	private BaseDAO baseDao = null;

	@Override
	public <T> Object add(String ns,T dto) throws Exception {
		return baseDao.add(ns, dto);
	}

	@Override
	public int deleteByKey(HashMap<String, Object> map)
			throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByDynamic(HashMap<String, Object> map)
			throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByKey(HashMap<String, Object> map)
			throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByDynamic(HashMap<String, Object> map)
			throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> List<T> findByKey(HashMap<String, Object> map)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> findByDynamic(HashMap<String, Object> map)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findCount(HashMap<String, Object> map) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBaseDao(BaseDAO baseDao) {
		// TODO Auto-generated method stub
		
	}

}
