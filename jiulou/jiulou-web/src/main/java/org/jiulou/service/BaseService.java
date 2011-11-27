package org.jiulou.service;

import java.util.HashMap;
import java.util.List;

import org.jiulou.dao.BaseDAO;

import com.beijing.common.BusinessException;

public interface BaseService extends GeneralService{
	public <T> Object add(String ns,T dto) throws Exception;
	public int deleteByKey(HashMap<String,Object> map) throws BusinessException;
	public int deleteByDynamic(HashMap<String,Object> map) throws BusinessException;
	public int updateByKey(HashMap<String,Object> map) throws BusinessException;
	public int updateByDynamic(HashMap<String,Object> map) throws BusinessException;
	public <T> List<T> findByKey(HashMap<String,Object> map) throws BusinessException;
	public <T> List<T> findByDynamic(HashMap<String,Object> map) throws BusinessException;
	public Long findCount(HashMap<String,Object> map) throws BusinessException;
	public void setBaseDao(BaseDAO baseDao);
}
