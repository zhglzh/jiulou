package org.jiulou.util.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jiulou.util.BeanUtil;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ControllerUtil {
	
	/**
	 * 把校验框架返回的错误代码转成消息，支持国际化
	 * @param result
	 * @param locale
	 * @return
	 */
	public static Map<String,String> translateValidatorMsgs(BindingResult result,Locale locale){
		
		ResourceBundleMessageSource messageSource = BeanUtil.getBean("messageSource", ResourceBundleMessageSource.class);
		
		Map<String,String> validatorMsgs = new HashMap<String,String>();
		for (Object object : result.getAllErrors()) {
			
			// 字段校验错误
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
		        validatorMsgs.put(fieldError.getField()
		        		,messageSource.getMessage(fieldError.getCode()
		        				, fieldError.getArguments(), fieldError.getDefaultMessage()
		        				, locale));
		    }

//		    // 全局错误 -- 貌似用不上？
//		    if(object instanceof ObjectError) {
//		        ObjectError objectError = (ObjectError) object;
//		        validatorMsgs.put(objectError.getObjectName()
//		        		,messageSource.getMessage(objectError.getCode()
//		        				, objectError.getArguments(), objectError.getDefaultMessage()
//		        				, locale));
//		    }
		}
		
		return validatorMsgs;
	}
}
