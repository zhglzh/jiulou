package org.jiulou.tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 从create table sql获取表元数据信息
 * @date 2011-11-07
 * @author zhanglizhi
 *
 */
public class TableMetadata {
	
	private String tableName = "";
	private String tableComment = "";
	private String className = "";
	private String objectName = "";
	private List<Column> tableColumns = new ArrayList<Column>();
	
	/**
	 * 解析sql语句
	 * @param sqlCreate
	 * @return
	 */
	public boolean parse(String sqlCreate){
		
		// "("
		int pos1 = sqlCreate.indexOf('(');
		// ")"
		int pos2 = sqlCreate.lastIndexOf(')');
		if (pos1 == -1 || pos2 == -1 || pos1 >= pos2){
			return false;
		}
		
		// get table
		String tmpStr = "";
		int i = sqlCreate.lastIndexOf(" ",pos1 - 2);
		tmpStr = sqlCreate.substring(i, pos1);
		this.tableName = tmpStr.trim().replace("`", "");
		this.className = toObjectName(this.tableName);
		this.objectName = make1stLowwer(this.className);
		
		tmpStr = sqlCreate.substring(pos2);
		i = tmpStr.indexOf("COMMENT");
		if (-1 == i) i = tmpStr.indexOf("comment");
		if ( -1 != i){
			tmpStr = tmpStr.substring(i + 9);
			i = tmpStr.lastIndexOf("'");
			this.tableComment = tmpStr.substring(0,i);
		}
		
		// get columns
		tmpStr = sqlCreate.substring(pos1 + 1,pos2 -1);
		
		// ,|结尾 或者,结尾 --勉强匹配
		//Pattern p1 = Pattern.compile("(.+?((',)|(,)))+?");
		Pattern p1 = Pattern.compile("(.+?,)+?");
		Matcher m1 = p1.matcher(tmpStr);
		while(m1.find()){
			String str = m1.group(1);
//			System.out.println("-----------");
//			System.out.println(m1.group(1));
			
			addOrModifyColumn(str);
		}
		
		return true;
	}
	
	@Override
	public String toString(){
		ObjectMapper mapper = new ObjectMapper();
		//mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 增加或者修改列
	 * @param str
	 */
	private void addOrModifyColumn(String str) {
		
		Column c = new Column();
		String s = getFieldName(str);
		c.setFieldName(s);
		c.setObjName(make1stLowwer(toObjectName(s)));
		c.setComment(getFieldComment(str));
		c.setAllowNull(getFieldAllowNull(str));
		c.setKey(getFieldIsKey(str));
		c.setAutoIncrement(checkIsAutoIncrement(str));
		
		FieldType ft = getColumnType(str);
		c.setFieldType(ft);
		switch(ft){
		case Char:
		case Varchar:
			c.setJavaType("String");
			this.tableColumns.add(c);
			break;
		case Int:
			c.setJavaType("int");
			this.tableColumns.add(c);
			break;
		case Tinyint:
			c.setJavaType("int");
			this.tableColumns.add(c);
			break;
		case Bigint:
			c.setJavaType("long");
			this.tableColumns.add(c);
			break;
		case Datetime:
			c.setJavaType("java.util.Date");
			this.tableColumns.add(c);
			break;
		case Other:
			String[] keys = checkConstraintPrimarykey(str);
			if (null != keys){
				for (String st:keys){
					Column c2 = getColumnByName(st);
					if (null != c2){
						c2.setKey(true);
					}
				}
			}
			break;
		default:
			
		}
	}
	
	/**
	 * 检查是否是自增列
	 * @param str
	 * @return
	 */
	private boolean checkIsAutoIncrement(String str) {
		if(str.toUpperCase().contains("AUTO_INCREMENT")){
			return true;
		}
		return false;
	}

	private Column getColumnByName(String columnName){
		
		for (Column c:this.tableColumns){
			if (c.getFieldName().equals(columnName)){
				return c;
			}
		}
		
		return null;
	}
	
	/**
	 * 检查是否是逐渐约束
	 * @param s
	 * @return
	 */
	private String[] checkConstraintPrimarykey(String s) {
		s = s.toLowerCase();
		if(s.contains("primary key")){
			int i = s.indexOf("(");
			int j = s.indexOf(")");
			if (j > i){
				s = s.substring(i + 1,j - 1);
				s = s.replace("`", "");
				return s.split(",");
			}
		}
		return null;
	}

	/**
	 * 检查是否是主键
	 * @param s
	 * @return
	 */
	private boolean getFieldIsKey(String s) {
		s = s.toLowerCase();
		if(s.contains("primary key")){
			return true;
		}
		return false;
	}

	/**
	 * 检查是否允许为空
	 * @param s
	 * @return
	 */
	private boolean getFieldAllowNull(String in) {
		String s = new String(in);
		s = s.toLowerCase();
		if(s.contains("not null")){
			return false;
		}
		return true;
	}

	/**
	 * 获取注释
	 * @param s
	 * @return
	 */
	private String getFieldComment(String in) {
		
		String s = new String(in);
		
		int i = 0;
		i = s.indexOf("COMMENT");
		if (-1 == i) i = s.indexOf("comment");
		if (-1 == i)
			return null;
		int j = s.lastIndexOf('\'');
		if (j == -1) return "";
		return s.substring(i + 9,j-1);
	}

	/**
	 * 获取列名
	 * @param str
	 * @return
	 */
	private String getFieldName(String str) {
		for(String s: str.split(" ")){
			if (null != s && !s.isEmpty())
				return s.replace("`", "");
		}
		return null;
	}

	/**
	 * 检查列类型
	 * @param s
	 * @return
	 */
	FieldType getColumnType(String s){
		if (s.contains("bigint(")){
			return FieldType.Bigint;
		}
		if (s.contains("varchar(")){
			return FieldType.Varchar;
		}		
		if (s.contains("int(")){
			return FieldType.Int;
		}
		if (s.contains("tinyint(")){
			return FieldType.Tinyint;
		}
		if (s.contains("datetime")){
			return FieldType.Datetime;
		}
		if (s.contains("char(")){
			return FieldType.Char;
		}
		return FieldType.Other;
	}
	
	enum FieldType{
		Char,
		Varchar,
		Int,
		Tinyint,
		Bigint,
		Datetime,
		Other
	}
	
	/**
	 * 将名称转成java对象名，如：
	 * CORP_USER --> corpUser
	 * USER_Login_Time --> userLoginTime
	 * @param s
	 * @return
	 */
	private String toObjectName(String s){
		
		StringBuilder sb = new StringBuilder();
		s = s.toLowerCase();
		
		// trim last "_" if exist
		if (s.charAt(s.length() - 1 ) == '_'){
			s = s.substring(0, s.length() - 2);
		}
		// trim first "_" if exist
		if (s.charAt(0) == '_'){
			s = s.substring(1);
		}
		
		s = make1stUpper(s);
		
		int p = s.indexOf('_');
		while (-1 != p){
			sb.append(s.substring(0,p));
			s = s.substring(p + 1);
			s = make1stUpper(s);
			
			p = s.indexOf('_',0);
		}
		sb.append(s);
		
		return sb.toString();
	}
	
	/**
	 * 首字母大写
	 * @param s
	 * @return
	 */
	private String make1stUpper(String in){
		if (null == in || in.isEmpty()) return in;
		
		String s = new String(in);
		
		String s1 = s.substring(0,1);
		String s2 = s.substring(1);
		
		return s1.toUpperCase() + s2;
	}
	
	/**
	 * 首字母小写
	 * @param s
	 * @return
	 */	
	private String make1stLowwer(String in){
		
		if (null == in || in.isEmpty()) return in;
		
		String s = new String(in);
		String s1 = s.substring(0,1);
		String s2 = s.substring(1);
		
		return s1.toLowerCase() + s2;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public List<Column> getTableColumns() {
		return tableColumns;
	}

	public void setTableColumns(List<Column> tableColumns) {
		this.tableColumns = tableColumns;
	}
}
