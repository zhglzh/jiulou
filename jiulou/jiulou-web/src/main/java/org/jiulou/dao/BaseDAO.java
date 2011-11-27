package org.jiulou.dao;

import java.util.List;
import java.util.Map;

/**
 * @author starsdust
 *
 */
public interface BaseDAO {
	/**
	 * 插入数据，返回行数
	 * @param <T>
	 * @param ns
	 * @param vo
	 * @return 行数
	 */
	public <T> int add(String ns,T vo);
	/**
	 * 插入数据，返回uuid
	 * @param <T>
	 * @param ns
	 * @param vo
	 * @return
	 */
	public <T> String addAndGetUuid(String ns,T vo);
	//public <T> T addAndGetVo(String ns,T vo);
	/**
	 * 批量插入数据，返回行数
	 * @param <T>
	 * @param ns
	 * @param vo
	 * @return
	 */	
	public <T> int addBatch(String ns,List<T> lst);
	
	/**
	 * 按主键更新， 不能更新null值到数据库；没有主键时，更新失败<br>
	 * 比如：map中有如下参数：user_name=null，则user_name:null会被忽略。<br>
	 * 如果user_name=""，则表中数据会被更新成""。<br>
	 * @param ns
	 * @param map
	 * @return
	 */
	public int updateByKey(String ns,Map<String,Object> map);
	
	/**
	 * 按主键删除，map中不含主键时，删除不了数据
	 * @param ns
	 * @param map
	 * @return
	 */
	public int deleteByKey(String ns,Map<String,Object> map);

	/**
	 * 根据uuid查找对象<br>
	 * 找不到时，返回null
	 * @param <T>
	 * @param ns
	 * @param uuid
	 * @return
	 */
	public <T> T findByUuid(String ns,String uuid);
	
	/**
	 * 按主键查找对象，没有时返回null
	 * @param <T>
	 * @param ns
	 * @param map
	 * @return
	 */
	public <T> T findByKey(String ns,Map<String,Object> map);
	
	/**
	 * 查找，返回满足条件的所有数据<br>多个条件间，用and连接
	 * @param <T>
	 * @param ns
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> find(String ns,Map<String,Object> map) throws Exception;
	
	/**
	 * 分页查找，返回满足条件的所有数据<br>多个条件间，用and连接<br>
	 * 默认按id排序
	 * @param <T>
	 * @param ns
	 * @param map
	 * @return
	 * 	start 从1开始
	 * 	limit 大于0
	 * 	ttl_cnt 满足条件的总条数
	 * 	cnt 本次返回的条数
	 * 	voList 数据list
	 * @throws Exception
	 */
	public <T> Map<String,Object> findByPage(String ns,Map<String,Object> map) throws Exception;	
	
	public Long findCount(String ns,Map<String,Object> map) throws Exception;
}
