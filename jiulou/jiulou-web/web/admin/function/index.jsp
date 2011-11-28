<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	request.setAttribute("WEB_ROOT_URI",request.getContextPath()); 
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="admin.index.title"/></title>
<%@ include file="../inc/css.inc" %>
<%@ include file="../inc/js.inc" %>
</head>
<body>
	<div id="body-wrapper">
		<!-- Wrapper for the radial gradient background -->
		<div id="sidebar">
			<jsp:include page="../inc/sidebar.jsp" flush="true">
				<jsp:param name="currentMenuId" value="functionsManage" />
			</jsp:include>
		</div>
		<!-- End #sidebar -->
		<div id="main-content">
					<h2>资源管理</h2>
			<p id="page-intro">What would you like to do?</p>
		<div class="content-box">
			<div class="content-box-header">
					<h3>资源列表</h3>
			</div>
			<div class="content-box-content">
			    <input type="button" value="关闭所有节点" onclick="expandAll('N')">  
			    <input type="button" value="展开所有节点" onclick="expandAll('Y')">  
			    <input type="button" value="取得当前行的数据" onclick="selectedItem()">
	    		当前选中的行：<input type="text" id="currentRow" size="60">  
	      
	    		<div id="div1"></div>
	
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
<script>  
    var config = {  
        id: "tg1",  
        width: "800",  
        renderTo: "div1",  
        headerAlign: "left",  
        headerHeight: "30",
        dataAlign: "left",
        indentation: "20",
        folderOpenIcon: "${WEB_ROOT_URI}/resource/treegrid/folder-blue-icon.png",
        folderCloseIcon: "${WEB_ROOT_URI}/resource/treegrid/folder-close-blue-icon.png",
        defaultLeafIcon: "${WEB_ROOT_URI}/resource/treegrid/folder-image-blue-icon.png",
        hoverRowBackground: "false",  
        folderColumnIndex: "1",  
        itemClick: "itemClickEvent",  
        expandLayer: 1,  
        columns:[  
            {headerText: "", headerAlign: "center", dataAlign: "center", width: "20", handler: "customCheckBox"},  
            {headerText: "名称", dataField: "name", headerAlign: "center", handler: "customOrgName"},  
            {headerText: "拼音码", dataField: "code", headerAlign: "center", dataAlign: "center", width: "100"},  
            {headerText: "负责人", dataField: "assignee", headerAlign: "center", dataAlign: "center", width: "100"},  
            {headerText: "查看", headerAlign: "center", dataAlign: "center", width: "100", handler: "customLook", folderHidden: true}  
        ],  
        data:[  
            {name: "城区分公司", code: "CQ", assignee: "", children:[  
                {name: "城区卡品分销中心"},  
                {name: "先锋服务厅", children:[  
                    {name: "chlid1"},  
                    {name: "chlid2"},  
                    {name: "chlid3", children: [  
                        {name: "chlid3-1"},  
                        {name: "chlid3-2"},  
                        {name: "chlid3-3"},  
                        {name: "chlid3-4"}  
                    ]}  
                ]},  
                {name: "半环服务厅"}  
            ]},  
            {name: "清新分公司", code: "QX", assignee: "", children:[]},  
            {name: "英德分公司", code: "YD", assignee: "", children:[]},  
            {name: "佛冈分公司", code: "FG", assignee: "", children:[]}  
        ]
    };  
  
    /* 
        单击数据行后触发该事件 
        id：行的id 
        index：行的索引。 
        data：json格式的行数据对象。 
    */  
    function itemClickEvent(id, index, data){  
        jQuery("#currentRow").val(id + ", " + index + ", " + TreeGrid.json2str(data));  
    }  
      
    /* 
        通过指定的方法来自定义栏数据 
    */  
    function customCheckBox(row, col){  
        return "<input type='checkbox'>";  
    }  
  
    function customOrgName(row, col){  
        var name = row[col.dataField] || "";  
        return name;
    }
  
    function customLook(row, col){  
        return "<a href='' style='color:blue;'>删除</a> - <a href='' style='color:blue;'>修改</a>";  
    }  
  
    //创建一个组件对象  
    var treeGrid = new TreeGrid(config);  
    treeGrid.show();  
      
    /* 
        展开、关闭所有节点。 
        isOpen=Y表示展开，isOpen=N表示关闭 
    */  
    function expandAll(isOpen){  
        treeGrid.expandAll(isOpen);  
    }  
      
    /* 
        取得当前选中的行，方法返回TreeGridItem对象 
    */  
    function selectedItem(){  
        var treeGridItem = treeGrid.getSelectedItem();  
        if(treeGridItem!=null){  
            //获取数据行属性值  
            //alert(treeGridItem.id + ", " + treeGridItem.index + ", " + treeGridItem.data.name);  
              
            //获取父数据行  
            var parent = treeGridItem.getParent();  
            if(parent!=null){  
                //jQuery("#currentRow").val(parent.data.name);  
            }  
              
            //获取子数据行集  
            var children = treeGridItem.getChildren();  
            if(children!=null && children.length>0){  
                jQuery("#currentRow").val(children[0].data.name);  
            }  
        }  
    }  
</script> 
<%@ include file="../inc/js-tail.inc" %>
</html>
