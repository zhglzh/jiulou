package com.beijing.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestShiroFilter extends PathMatchingFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(TestShiroFilter.class);
	
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
		HttpServletRequest reqs = (HttpServletRequest)request;
		if (null != reqs)
			logger.info(reqs.getRequestURL().toString());
        return true;
    }
}
