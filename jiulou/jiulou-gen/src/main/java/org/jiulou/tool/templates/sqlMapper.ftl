<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign today = .now>
<!--
 * ${tm.tableComment}
 * @author Gentool
 * @date ${today?string("yyyy-MM-dd HH:mm:ss")}
-->
<mapper namespace="org.jiulou.dao.${tm.objectName}">

	<sql id="whereKeys">
		<trim prefix="where" prefixOverrides="and">
		 <#list tm.tableColumns as column>
			<#if (column.isKey)>
				and ${column.fieldName} = ${r"#{"}${column.objName}${"}"}
			</#if>
		 </#list>
		</trim>
	</sql>
	<sql id="whereOthers">
		<trim prefix="where" prefixOverrides="and">
		 <#list tm.tableColumns as column>
			<#if !column.isKey>
				and ${column.fieldName} = ${r"#{"}${column.objName}${"}"}
			</#if>
		 </#list>
		</trim>
	</sql>
	<sql id="selectColumns">
		<trim prefix="," prefixOverrides="">
		 <#list tm.tableColumns as column>
			,${column.fieldName} as ${column.objName}
		 </#list>
		</trim>
	</sql>

 <insert id="add" parameterType="org.jiulou.vo.${tm.className}">
  	 insert into ${tm.tableName} (
		<#list tm.tableColumns as column>
			<#if !(column.isAutoIncrement)>
			${column.fieldName},
			</#if>
		</#list>
  	 )
  	 values (
		<#list tm.tableColumns as column>
		<#if !(column.isAutoIncrement)>
			${r"#{"}${column.objName}${"}"},
		</#if>
		</#list>
  	 )
 </insert>
 
<insert id="addAndGetKey" parameterType="org.jiulou.vo.${tm.className}">
	<selectKey keyProperty="id" resultType="long" order="AFTER">
	    select id from user where uuid=${r"#{"}uuid${"}"}
	</selectKey>
  	 insert into ${tm.tableName} (
		<#list tm.tableColumns as column>
			<#if !(column.isAutoIncrement)>
			${column.fieldName},
			</#if>
		</#list>
  	 )
  	 values (
		<#list tm.tableColumns as column>
		<#if !(column.isAutoIncrement)>
			${r"#{"}${column.objName}${"}"},
		</#if>
		</#list>
  	 )
 </insert>
 
 <insert id="addBatch" parameterType="List">
  	 insert into ${tm.tableName} (
		<#list tm.tableColumns as column>
			<#if !(column.isAutoIncrement)>
			${column.fieldName},
			</#if>
		</#list>
  	 )values
	<foreach collection = "list" item = "p" open = "(" close = ")" separator = "" >
		<#list tm.tableColumns as column>
			<#if !(column.isAutoIncrement)>
			${r"#{p."}${column.objName}${"}"},
			</#if>
		</#list>
    </foreach >
 </insert>
 
	<select id="select" parameterType="HashMap" resultType="org.jiulou.vo.${tm.className}">
	  SELECT
			<include refid="selectColumns"/>
	  FROM PERSON 
	  	<include refid="whereOthers"/>
		<include refid="whereKeys"/>
	</select>
	<select id="selectByKey" parameterType="HashMap" resultType="org.jiulou.vo.${tm.className}">
	  SELECT
			<include refid="selectColumns"/>
	  FROM PERSON
	  	<include refid="whereKeys"/>
	</select>
	<select id="selectByPage" parameterType="HashMap" resultType="org.jiulou.vo.${tm.className}">
		SELECT
			<include refid="selectColumns"/>
	  FROM PERSON 
	  	<include refid="whereOthers"/>
		<include refid="whereKeys"/>
	</select>

	<update id="updateByKey" parameterType="HashMap">
		update ${tm.tableName}
		<set>
			 <#list tm.tableColumns as column>
				<#if column.isKey != true>
				    <if test="${column.objName} != null">${column.fieldName}=${r"#{"}${column.objName}${"}"},</if>
				</#if>
			 </#list>	
		</set>
		where
		<include refid="whereKeys"/>
	</update>
	
	<delete id="deleteByKey" parameterType="HashMap" flushCache="true" statementType="PREPARED" timeout="20000">
	delete from ${tm.tableName} 
	<include refid="whereKeys"/>
	</delete>
</mapper>