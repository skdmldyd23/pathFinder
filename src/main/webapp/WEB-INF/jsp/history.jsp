<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<sec:authentication property="principal.username" var="authUsername" />
<sec:authentication property="principal.authorities" var="userAuth" />

<!DOCTYPE html>
<html>
<head>
<title>Document</title>
<!-- Toggle checkbox -->
<link
	href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
	rel="stylesheet">
<script
	src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

<link rel="stylesheet" href="static/css/history/print.css">
</head>
<body>
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-md-2 col-md-6">
					<h1>조회내역</h1>
				</div>
				<div class="col-md-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item"><a href="/home">홈</a></li>
						<li class="breadcrumb-item active">운송조회</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

	<div class="container-fluid ">
		<div class="card height100">
			<div class="card-header">
				<div class="row">
				<ul class="nav nav-pills col-sm-9">
					<li class="nav-item"><a class="nav-link" href="#todayList"
						id="will" onclick="getHistory(0, this.id)" data-toggle="tab">운송예정</a></li>
					<li class="nav-item"><a class="nav-link active" id="ing"
						onclick="getHistory(0, this.id)" href="#afterList"
						data-toggle="tab">운송중</a></li>
					<li class="nav-item"><a class="nav-link" href="#beforeList"
						id="pp" onclick="getHistory(0, this.id)" data-toggle="tab">운송완료</a></li>
				</ul>
				
					<div class="card-tools col-sm-3">
					<div class="row">
						<div class="col-sm-5">
							<input id="myhistory" type="checkbox" data-toggle="toggle"
								data-size="small" data-on="내 예약약" data-off="전체" data-onstyle="info"/>
						</div>
						<div class="col-sm-7">
							<div class=" input-group input-group-sm col-sm-12">
								<input class="form-control form-control-navbar " type="search"
									placeholder="출발날짜 선택" id="keyword" autocomplete="off" readonly />
								<div class="input-group-append">
									<button class="btn btn-sm bg-olive" name="btnSearch"
										id="btnSearch" onclick="getSearch()">
										<i class="fas fa-search"></i>
									</button>
								</div>
							</div>
							<div id="calendar"></div>
						</div>
					</div>
				</div>
				</div>
			</div>

			<div class="card-body box-profile table-responsive p-0 height100">
				<div class="tab-content">
					<table class="table table-hover">
						<thead>
							<tr>
								<th style="width: 10%">예약일자</th>
								<th style="width: 10%">사용자</th>
								<th style="width: 10%">출발지</th>
								<th style="width: 10%">도착지</th>
								<th style="width: 10%">출발일자</th>
								<th style="width: 10%">도착일자</th>
								<th style="width: 10%">차량번호</th>
								<th style="width: 10%">상세보기</th>
							</tr>
						</thead>
						<tbody id="tableListBody" class="small">
						</tbody>
					</table>
				</div>
				<!-- /.tab-content -->
				<!-- /.card-body -->
				<!-- /.nav-tabs-custom -->
			</div>
		</div>
		<div id="page"></div>
	</div>
</body>
<%@include file="historyModal.jsp"%>
<%@include file="alert.jsp"%>

<script src="/static/js/history.js"></script>
<script type="text/javascript">
    let userName = "${authUsername}";
    let userAuth = "${userAuth}";
  </script>
</html>
