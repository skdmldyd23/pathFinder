<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Document</title>
</head>

<body>
	<section class="content-header  ">
		<div class="container-fluid">
			<div class="row mb-2 ">
				<div class="col-md-2 col-md-6 ">
					<h1>조직도</h1>
				</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb float-sm-right " id="headInfo">
						<li class="breadcrumb-item"><a href="/home">홈</a></li>
						<li class="breadcrumb-item active">조직도</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

	<div class="container-fluid height675px">
		<div class="row vh-80">
			<div class="col-md-2">
				<div class="card height100">
					<div class="card-header">
						<h3 class="card-title" style="height: 30px">지점 목록</h3>
					</div>
					<div class="card-body small height675px">
						<div id="jstree" class="myScrollableBlock scrollbar-outer"></div>
					</div>
					
				</div>
			</div>

			<div class="col-md-10">
				<div class="card height100">
					<div class="card-header">
						<h3 class="card-title">사원 목록</h3>

						<div class="card-tools ">
							<div class="input-group input-group-sm">
								<select class=" col-4 small " name="searchType" id="searchType">
									<option selected value="name" class="small">이름</option>
									<option value="position" class="small">직책</option>
								</select> <input class="col-sm-7 form-control form-control-navbar"
									type="search" placeholder="Search" id="searchInput" />
								<div class="input-group-append">
									<button class="btn btn-sm bg-olive" name="btnSearch"
										type="button" id="searchButton" onclick="getSearch()">
										<i class="fas fa-search"></i>
									</button>

								</div>
							</div>
						</div>


					</div>

					<div class="card-body table-responsive p-0">
						<table class="table table-hover">
							<thead>
								<tr>
									<th style="width: 15%">이름</th>
									<th style="width: 25%">이메일</th>
									<th style="width: 20%">전화번호</th>
									<th style="width: 15%">지점명</th>
									<th style="width: 25%">직책</th>
								</tr>
							</thead>

							<tbody id="userTable" class="small"></tbody>
						</table>
						
					</div>
				</div>
			</div>
		</div>
		<div id="page"></div>
	</div>

	<script src="/static/js/hierarchy.js"></script>
</body>
</html>
