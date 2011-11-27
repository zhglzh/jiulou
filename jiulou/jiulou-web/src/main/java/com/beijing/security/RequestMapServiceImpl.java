package com.beijing.security;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 从数据库查询URL--授权定义的RequestMapService实现类.
 * 获取所有的资源，及资源对应的权限
 * 
 * @author 张立志
 */
public class RequestMapServiceImpl implements RequestMapService {
	
	public LinkedHashMap<String, String> getRequestMap() {
		
		HashMap<String,String> map = null;
		// 无参数
		return null;
	}
}
