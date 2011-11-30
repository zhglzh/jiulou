package org.jiulou.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanUtilImpl implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		BeanUtilImpl.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		return (T) BeanUtilImpl.applicationContext.getBean(beanId);
	}
	
	public static <T> T getBean(String beanId,Class<T> type){
		return BeanUtilImpl.applicationContext.getBean(beanId,type);
	}
}
