<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sidebar-wrapper">
	<!-- Sidebar with logo and menu -->
	<h1 id="sidebar-title">
		<a href="#">Simpla Admin</a>
	</h1>
	<!-- Logo (221px wide) -->
	<a href="#"><img id="logo" src="<%=request.getContextPath()%>/admin/resources/images/logo.png"
		alt="Simpla Admin logo" />
	</a>
	<!-- Sidebar Profile links -->
	<div id="profile-links">
		Hello, <a href="#" title="Edit your profile">Admin</a>, you have <a
			href="#messages" rel="modal" title="3 Messages">3 Messages</a><br />
		<br /> <a href="#" title="View the Site">View the Site</a> | <a
			href="#" title="Sign Out">Sign Out</a>
	</div>
	<ul id="main-nav">
		<!-- Accordion Menu -->
		<li><a id="Dashboard" href="<%=request.getContextPath()%>/admin/" class="nav-top-item no-submenu"> <!-- Add the class "no-submenu" to menu items with no sub menu -->
				Dashboard</a></li>
		<li>
			<a id="functions" href="#" class="nav-top-item" title="所有站内资源，如URL、功能菜单">资源（权限）管理</a>
			<ul>
				<li><a id="functionsManage" href="<%=request.getContextPath()%>/admin/function/">管理资源（权限）</a></li>
				<li><a id="functionsAdd" href="<%=request.getContextPath()%>/admin/function/add.jsp">添加新的资源</a></li>
			</ul>
		</li>
		<li>
			<a href="#" class="nav-top-item">公司</a>
			<ul>
				<li><a href="#" title="开通、停止；管理公司的权限">公司管理</a></li>
				<li><a href="#">添加新的公司</a></li>
				<li><a href="#" title="允许公司使用哪些功能？">公司权限管理</a></li>
			</ul>
		</li>
		<li><a href="#" class="nav-top-item"> <!-- Add the class "current" to current menu item -->
				Articles </a>
			<ul>
				<li><a href="#">Write a new Article</a>
				</li>
				<li><a href="#">Manage Articles</a>
				</li>
				<!-- Add class "current" to sub menu items also -->
				<li><a href="#">Manage Comments</a>
				</li>
				<li><a href="#">Manage Categories</a>
				</li>
			</ul>
		</li>
		<li><a href="#" class="nav-top-item"> Pages </a>
			<ul>
				<li><a href="#">Create a new Page</a>
				</li>
				<li><a href="#">Manage Pages</a>
				</li>
			</ul></li>
		<li><a href="#" class="nav-top-item"> Image Gallery </a>
			<ul>
				<li><a href="#">Upload Images</a>
				</li>
				<li><a href="#">Manage Galleries</a>
				</li>
				<li><a href="#">Manage Albums</a>
				</li>
				<li><a href="#">Gallery Settings</a>
				</li>
			</ul></li>
		<li><a href="#" class="nav-top-item"> Events Calendar </a>
			<ul>
				<li><a href="#">Calendar Overview</a>
				</li>
				<li><a href="#">Add a new Event</a>
				</li>
				<li><a href="#">Calendar Settings</a>
				</li>
			</ul></li>
		<li><a href="#" class="nav-top-item"> Settings </a>
			<ul>
				<li><a href="#">General</a>
				</li>
				<li><a href="#">Design</a>
				</li>
				<li><a href="#">Your Profile</a>
				</li>
				<li><a href="#">Users and Permissions</a>
				</li>
			</ul></li>
	</ul>
	<script>
	$(document).ready(function(){
		<%
			String currentMenuId = request.getParameter("currentMenuId");
		%>
		var currentMenuId = $("#<%=currentMenuId%>");
		currentMenuId.addClass("current");
		
		var a = currentMenuId.parent().parent() ;
		if((a.attr("id")) != "main-nav"){
			a.parent().find("a.nav-top-item").addClass("current");
		}
	});
	</script>
	
	<!-- End #main-nav -->
	<div id="messages" style="display: none">
		<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
		<h3>3 Messages</h3>
		<p>
			<strong>17th May 2009</strong> by Admin<br /> Lorem ipsum dolor
			sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi
			at felis aliquet congue. <small><a href="#"
				class="remove-link" title="Remove message">Remove</a>
			</small>
		</p>
		<p>
			<strong>2nd May 2009</strong> by Jane Doe<br /> Ut a est eget
			ligula molestie gravida. Curabitur massa. Donec eleifend, libero
			at sagittis mollis, tellus est malesuada tellus, at luctus turpis
			elit sit amet quam. Vivamus pretium ornare est. <small><a
				href="#" class="remove-link" title="Remove message">Remove</a>
			</small>
		</p>
		<p>
			<strong>25th April 2009</strong> by Admin<br /> Lorem ipsum dolor
			sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi
			at felis aliquet congue. <small><a href="#"
				class="remove-link" title="Remove message">Remove</a>
			</small>
		</p>
		<form action="#" method="post">
			<h4>New Message</h4>
			<fieldset>
				<textarea class="textarea" name="textfield" cols="79" rows="5"></textarea>
			</fieldset>
			<fieldset>
				<select name="dropdown" class="small-input">
					<option value="option1">Send to...</option>
					<option value="option2">Everyone</option>
					<option value="option3">Admin</option>
					<option value="option4">Jane Doe</option>
				</select> <input class="button" type="submit" value="Send" />
			</fieldset>
		</form>
	</div>
	<!-- End #messages -->
</div>