<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<tiles:insertAttribute name="header" />
<html>

<body
	class="hold-transition sidebar-mini layout-fixed layout-navbar-fixed">
	<div class="wrapper">
		<tiles:insertAttribute name="nav" />

		<!-- Sidebar -->
		<tiles:insertAttribute name="left" />
		<div id="layout" class="content-wrapper">
			<tiles:insertAttribute name="body" />
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
<script type="text/javascript">
	$('.scrollbar-outer').scrollbar();
</script>
</html>
