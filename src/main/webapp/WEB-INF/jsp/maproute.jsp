<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<title>CSS</title>

<!-- 지도 캡쳐 -->
<script src="/static/js/route/print.js"></script>
<script src="/static/js/route/domtoimage.js"></script>

<!-- 한국 지도 표시 -->
<%-- <script src="/static/js/route/koreaMap/proj4.js"></script>
<script src="/static/js/route/koreaMap/proj4leaflet.js"></script>
<script src="/static/js/route/koreaMap/Leaflet.KoreanTmsProviders.js"></script> --%>

<link href="/static/css/route/timeline.css" rel="stylesheet" />
</head>

<body>
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-md-2 col-md-6">
					<h1>운송등록</h1>
				</div>
				<div class="col-md-6">
					<ol class="breadcrumb float-sm-right" id="headInfo">
						<li class="pr-3"><button class="float-sm-right btn bg-olive"
								id="testButton">테스트</button></li>
						<li class="breadcrumb-item"><a href="/home">홈</a></li>
						<li class="breadcrumb-item active">운송 등록</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>

	<div class="container-fluid" id="testCap">
		<div class="row">
			<div class="col-md-9">
				<div class="vh-85" id="map"></div>
			</div>
			<div class="col-md-3">
				<form id="routeForm">
					<div class="accordion md-accordion" id="accordion-select">
						<!-- 출발지점 선택 -->
						<div class="card">
							<div class="card-header" id="headingDep">
								<h3 class="card-title">1. 출발 지점 선택</h3>
							</div>

							<div id="col-selectDep" class="collapse show"
								aria-labelledby="headingDep" data-parent="#accordion-select">
								<div class="card-body">
									<select id="depSelect" name="depSelect">
										<option></option>
									</select>
								</div>

								<div class="card-footer text-center">
									<button id="btn1" class="btn bg-olive float-right next"
										type="button">다음</button>
								</div>
							</div>
						</div>

						<!-- 차량 선택 -->
						<div class="card">
							<div class="card-header" id="headingCar">
								<h3 class="card-title">2. 차량 선택</h3>
							</div>

							<div id="col-selectCar" class="collapse"
								aria-labelledby="headingCar" data-parent="#accordion-select">
								<div class="card-body">
									<select id="carSelect" name="carSelect">
										<option></option>
									</select>
								</div>

								<div class="card-footer text-right">
									<button class="btn btn-outline-success" type="button"
										data-toggle="collapse" data-target="#col-selectDep"
										aria-expanded="false" aria-controls="col-selectDep">
										이전</button>
									<button id="btn2" class="btn bg-olive next" type="button">다음</button>
								</div>
							</div>
						</div>

						<!-- 날짜선택 -->
						<div class="card">
							<div class="card-header" id="headingDate">
								<h3 class="card-title">3. 예약 날짜 선택</h3>
							</div>

							<div id="col-selectDate" class="collapse"
								aria-labelledby="headingDate" data-parent="#accordion-select">
								<div class="card-body" id="calendarcard">
									<div id="calendarBox"></div>

									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"><i
												class="far fa-calendar-alt"></i></span>
										</div>
										<input type="text" class="form-control" id="dateSelect"
											name="dateSelect" readonly />
									</div>
									<!-- /.input group -->
								</div>

								<div class="card-footer text-right">
									<button class="btn btn-outline-success" type="button"
										data-toggle="collapse" data-target="#col-selectCar"
										aria-expanded="false" aria-controls="col-selectCar">
										이전</button>
									<button id="btn3" class="btn bg-olive next" type="button"
										onclick="branchlist(selectBranchlist)">다음</button>
								</div>
							</div>
						</div>

						<!-- 지점 선택 -->
						<div class="card">
							<div class="card-header" id="headingBranch">
								<h3 class="card-title">4. 경유 지점 선택</h3>
							</div>

							<div id="col-selectBranch" class="collapse"
								aria-labelledby="headingBranch" data-parent="#accordion-select">
								<div class="card-body">
									<select id="branchSelect" name="branchSelect">
									</select>
								</div>
								<div class="card-footer text-right">
									<button class="btn btn-outline-success" type="button"
										data-toggle="collapse" data-target="#col-selectDate"
										aria-expanded="false" aria-controls="col-selectDate">
										이전</button>
									<button class="btn bg-olive" id="resultButton" type="button">
										다음</button>
								</div>
							</div>
						</div>

						<!-- 경로 타임라인 -->
						<div class="card">
							<div class="card-header" id="headingRoad">
								<h3 class="card-title">5. 추천 경로</h3>
							</div>

							<div id="col-selectRoad" class="collapse"
								aria-labelledby="headingRoad" data-parent="#accordion-select">
								<div class="card-body">
									<div id="routeResult" class="scrollbar-outer">
										<div class="tmline"></div>
									</div>
								</div>
								<div id="tmlineResult"></div>

								<div class="card-footer text-right">
									<button class="btn btn-outline-success" id="resultPrev"
										type="button" data-toggle="collapse"
										data-target="#col-selectBranch" aria-expanded="false"
										aria-controls="col-selectBranch">이전</button>
									<button class="btn bg-olive btn-success" type="submit">
										등록</button>

									<div class="float-left distSwitch">
										<div class="custom-control custom-switch">
											<input type="checkbox" class="custom-control-input"
												id="showSortDist" /> <label class="custom-control-label"
												for="showSortDist">거리 우선</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--  accordion div -->
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="/static/js/routes.js"></script>
</body>

<%@include file="alert.jsp"%>
</html>
