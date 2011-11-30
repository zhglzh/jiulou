<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="admin.index.title"/></title>
<%@ include file="../inc/css.inc" %>
<%@ include file="../inc/js.inc" %>
    <script type="text/javascript" src="jquery.form.js"></script>

    <script type="text/javascript">
        // wait for the DOM to be loaded
        $(document).ready(function() {
        	<c:if test="${rtn.code == '0' }">
        		alert("插入成功");
        		window.close();
        	</c:if>
            // bind 'myForm' and provide a simple callback function
            $('#frmAdd__').ajaxForm({
            	success: function() {
                    $('#htmlExampleTarget').fadeIn('slow');
                }
            });
        });
    </script>
</head>
<body>
<br>
<h3>添加新的公司</h3>

<c:if test="${fieldErrors != null }">
<c:forEach items="${fieldErrors}" var="error"> 
       <c:out value="${error.key}"></c:out>|<c:out value="${error.value}"></c:out><br>
</c:forEach>
</c:if>

<form id="frmAdd" name="frmAdd" action="/corporations/add.do" method="post">
	
	<fieldset>
		<legend>公司信息</legend>
		<!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->
		<p>
			<label>公司名称</label> 
			<input id="deptName" name="deptName" value="${vo.deptName}" class="text-input small-input" type="text" />
				<span class="input-notification success png_bg">${fieldErrors.deptName}</span>
			<!-- Classes for input-notification: success, error, information, attention -->
			<br /> <small>A small description of the field</small>
		</p>
		<p>
			<label>公司编码</label>
			<input class="text-input small-input" type="text" id="deptCode" name="deptCode"/>
			<span class="input-notification error png_bg">Error message</span>
		</p>
		<p>
		<label>有效期至</label>
		<input  id="validTime" name="validTime" value="${vo.validTime}" type="text" class="datepicker text-input small-input"onclick="WdatePicker()" />
			<span class="input-notification error png_bg">${fieldErrors.validTime}</span>
		</p>
		<p>
			<label>Radio buttons</label> <input type="radio" name="radio1" />
			This is a radio button<br /> <input type="radio" name="radio2" />
			This is another radio button
		</p>
		<p>
			<label>status</label> <select name="dropdown"
				class="small-input">
				<option value="1">Option 1</option>
				<option value="2">Option 2</option>
				<option value="3">Option 3</option>
				<option value="4">Option 4</option>
			</select>
		</p>
		<p>
			<label>Info</label>
			<textarea class="text-input textarea wysiwyg" id="desc"
				name="desc" cols="79" rows="15"></textarea>
		</p>
		<p>
			<input class="button" type="submit" value="Submit" /> <input class="button" type="reset" value="Reset" />
		</p>
	</fieldset>
	<div class="clear"></div>
	<!-- End .clear -->
</form>
<div id="htmlExampleTarget" style="display:none">
成功
</div>
</body>
</html>