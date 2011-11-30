package org.jiulou.web.validator;

import org.jiulou.vo.Corporation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("corporationValidator")
public class CorporationValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Corporation.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deptName"
				,"corporation.field.deptName.required","公司名称必须输入");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "validTime"
				,"corporation.field.deptName.required","必须输入有效期至");		
	}
}
