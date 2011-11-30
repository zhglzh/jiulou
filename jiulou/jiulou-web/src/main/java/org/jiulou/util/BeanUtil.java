package org.jiulou.util;


public class BeanUtil {
	
	/**
	 * 仅能在spring启动完毕后使用
	 * @param <T>
	 * @param beanId
	 * @return
	 */
	public static <T> T getBean(String beanId){
		return BeanUtilImpl.getBean(beanId);
	}
	
	/**
	 * 仅能在spring启动完毕后使用
	 * @param <T>
	 * @param beanId
	 * @return
	 */	
	public static <T> T getBean(String beanId,Class<T> type){
		return BeanUtilImpl.getBean(beanId,type);
	}	
}
