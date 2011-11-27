package org.jiulou.vo;
<#assign today = .now>
/**
 * ${tm.tableComment}
 * @author Gentool
 * @date ${today?string("yyyy-MM-dd HH:mm:ss")}
 */
public class ${tm.className} {

	<#list tm.tableColumns as column>
		private ${column.javaType} ${column.objName};
	</#list>

}
