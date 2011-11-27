package org.jiulou.tool;

import org.jiulou.tool.TableMetadata.FieldType;

public class Column {
	
	private String fieldName = "";
	private FieldType fieldType = FieldType.Varchar;
	private boolean isKey = false;
	private boolean isAllowNull = false;
	private String comment = "";
	private String objName = "";
	private String javaType = "";
	private boolean isAutoIncrement = false;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public FieldType getFieldType() {
		return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	public boolean getIsKey() {
		return isKey;
	}
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}
	public boolean getIsAllowNull() {
		return isAllowNull;
	}	
	public boolean isAllowNull() {
		return isAllowNull;
	}
	public void setAllowNull(boolean isAllowNull) {
		this.isAllowNull = isAllowNull;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
//	public boolean isAutoIncrement() {
//		return isAutoIncrement;
//	}
	public boolean getIsAutoIncrement() {
		return isAutoIncrement;
	}	
	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}
}
