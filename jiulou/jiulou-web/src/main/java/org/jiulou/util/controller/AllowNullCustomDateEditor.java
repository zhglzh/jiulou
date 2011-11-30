package org.jiulou.util.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.jiulou.util.DatetimeUtil;

/**
 * 允许为空的
 * @author zhanglizhi
 *
 */
public class AllowNullCustomDateEditor extends PropertyEditorSupport{
	
    public void setAsText(String value) {
        try {  
            setValue(DatetimeUtil.DATE_FORMAT.parse(value));  
        } catch(ParseException e) {  
            setValue(null);  
        }
    }  

    public String getAsText() {
        return DatetimeUtil.DATE_FORMAT.format((Date) getValue());  
    }
}
