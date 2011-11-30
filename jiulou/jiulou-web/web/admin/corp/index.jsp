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
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	$('#dataTable').dataTable( {
		"bProcessing": true,
		"bServerSide": true,
		"sAjaxSource": "scripts/post.php",
		"fnServerData": function ( sSource, aoData, fnCallback ) {
			$.ajax( {
				"dataType": 'json', 
				"type": "POST", 
				"url": sSource, 
				"data": aoData, 
				"success": fnCallback
			} );
		}
	} );
} );
</script>
</head>
<body>
	<div id="body-wrapper">
		<!-- Wrapper for the radial gradient background -->
		<div id="sidebar">
			<jsp:include page="../inc/sidebar.jsp" flush="true">
				<jsp:param name="currentMenuId" value="corpsManage" />
			</jsp:include>
		</div>
		<!-- End #sidebar -->
		<div id="main-content">
					<h2>公司管理</h2>
			<p id="page-intro">What would you like to do?</p>
		<div class="content-box">
			<div class="content-box-header">
					<h3>公司列表</h3>
			</div>
			<div class="content-box-content">
			    <input type="button" value="关闭所有节点" onclick="expandAll('N')">  
			    <input type="button" value="展开所有节点" onclick="expandAll('Y')">  
			    <input type="button" value="取得当前行的数据" onclick="selectedItem()">
	    		当前选中的行：<input type="text" id="currentRow" size="60">  
	      
	    		<div id="div1">
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="dataTable">
						<thead>
							<tr>
								<th width="20%">Rendering engine</th>
								<th width="25%">Browser</th>
								<th width="25%">Platform(s)</th>
								<th width="15%">Engine version</th>
								<th width="15%">CSS grade</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="5" class="dataTables_empty">Loading data from server</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th>Rendering engine</th>
								<th>Browser</th>
								<th>Platform(s)</th>
								<th>Engine version</th>
								<th>CSS grade</th>
							</tr>
						</tfoot>
					</table>	    		
	    		</div>
	
	    	</div>
    	</div>
			<div id="footer">
				<c:import url="../inc/footer.jsp"/>
			</div>
			<!-- End #footer -->
		</div>
		<!-- End #main-content -->
	</div>
</body>
<%@ include file="../inc/js-tail.inc" %>
</html>
