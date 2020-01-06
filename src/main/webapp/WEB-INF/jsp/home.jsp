<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Document</title>

<link
	href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
	rel="stylesheet">
<script
	src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
</head>
<body>
	<div class="content-header"></div>
	<section class="content">
		<div class="container-fluid">
			<div class="row">
				<div class="col-12 col-sm-6 col-md-3">
					<div class="info-box">
						<span class="info-box-icon bg-info elevation-1"> <i
							class="fas fa-shipping-fast"></i></span>

						<div class="info-box-content">
							<span class="info-box-text">오늘의 운송현황</span> <span
								class="info-box-number" id="todayPercent"> </span>
						</div>
					</div>
				</div>
				<div class="col-12 col-sm-6 col-md-3">
					<div class="info-box mb-3">
						<span class="info-box-icon bg-danger elevation-1"><i
							class="fas fa-users"></i></span>

						<div class="info-box-content">
							<span class="info-box-text">전체 사용자</span> <span
								class="info-box-number" id="userCount"></span>
						</div>
					</div>
				</div>
				<div class="clearfix hidden-md-up"></div>

				<div class="col-12 col-sm-6 col-md-3">
					<div class="info-box mb-3">
						<span class="info-box-icon bg-green elevation-1"> <i
							class="far fa-building"></i>
						</span>

						<div class="info-box-content">
							<span class="info-box-text">지점</span> <span
								class="info-box-number" id="branchCount"></span>
						</div>
					</div>
				</div>
				<div class="col-12 col-sm-6 col-md-3">
					<div class="info-box mb-3">
						<span class="info-box-icon bg-warning elevation-1"> <i
							class="fas fa-history"></i></span>

						<div class="info-box-content">
							<span class="info-box-text">기록</span> <span
								class="info-box-number" id="totalHistoryCount"></span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-8">
					<div class="row">
						<div class="col-md-12">
							<div class="card">
								<div class="card-header">
									<h5 class="card-title">
										<i class="fas fa-truck mr-1"></i>운송 현황(최근 한달)
									</h5>

									<div class="card-tools">
										<input id="myDelivery" type="checkbox" data-toggle="toggle"
											data-size="small" data-on="내 운송송" data-off="전체" data-onstyle="info"/>
										<button type="button" class="btn btn-tool"
											data-card-widget="collapse">
											<i class="fas fa-minus"></i>
										</button>
									</div>
								</div>
								<div class="card-body">
									<div class="row">
										<div class="col-md-7">
											<div id="doughnutDiv" class="canvas">
												<canvas id="chart-area" class="chart-js-render-monitor"></canvas>
											</div>
										</div>
										<div class="col-md-5">
											<p class="text-center">
												<strong>진행현황</strong>
											</p>

											<div class="progress-group">
												운송예정 <span id="willProgress" class="float-right"></span>
												<div class="progress progress-sm active">
													<div id="willDiv" class="progress-bar bg-danger"></div>
												</div>
											</div>

											<div class="progress-group">
												운송중 <span id="ingProgress" class="float-right"></span>
												<div class="progress progress-sm active">
													<div id="ingDiv" class="progress-bar bg-warning"></div>
												</div>
											</div>

											<div class="progress-group">
												<span class="progress-text">운송완료</span> <span
													id="ppProgress" class="float-right"></span>
												<div class="progress progress-sm active">
													<div id="ppDiv" class="progress-bar bg-success"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card card-rmwl card-outline card-outline-tabs">
						<div class="card-header">
							<div class="card-title">
								<i class="far fa-calendar-alt mr-1"></i>스케줄
							</div>
							<div class="card-tools">
								<a href="/history" class="btn btn-sm btn-secondary">전체보기</a>
								<button type="button" class="btn btn-tool"
									data-card-widget="collapse">
									<i class="fas fa-minus"></i>
								</button>
							</div>
						</div>
						<!-- /.card-header -->
						<div class="card-body p-0 ">
							<ul class="nav nav-tabs p-0 pt-1 border-bottom-0 ">
								<li class="nav-item ">
									<a class="nav-link active " href="#todayHistory"
									data-toggle="tab" onclick="todayHistory()">
										오늘의 스케줄
									</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="#recentlyHistory" 
									data-toggle="tab" onclick="recentlyHistory()">
										나의 히스토리
									</a>
								</li>
							</ul>

						<div class="table-responsive">
							<table class="table m-0">

								<thead>
									<tr> 
										<th style="width:10%"></th>
										<th style="width:19%">출발일자</th>
										<th style="width:19%">도착일자</th>
										<th style="width:12%">사용자</th>
										<th style="width:13%">출발지</th>
										<th style="width:13%">도착지</th>
										<th style="width:15%">차량번호</th>
									</tr>
								</thead>
								<tbody id="schedule">
								</tbody>
							</table>
						</div>
						</div>
					</div>
				</div>
				<!-- /.col -->

				<div class="col-md-4">
					<div class="row ">
						<div class="col-md-12">
							<div class="card">
								<div class="card-header">
									<h3 class="card-title">
										<i class="fas fa-money-bill-wave mr-1"></i>지역별 지점비
									</h3>

									<div class="card-tools">
										<button type="button" class="btn btn-tool"
											data-card-widget="collapse">
											<i class="fas fa-minus"></i>
										</button>
									</div>
								</div>
								
								<div class="card-body p-0">
									<div class="row">
										<div class="col-md-12">
											<div id="canvas">
												<div id="south"></div>
												<div id="seoul"></div>
												<div id="gygg"></div>
												<div id="incheon"></div>
												<div id="gangwon"></div>
												<div id="chungbuk"></div>
												<div id="chungnam"></div>
												<div id="daejeon"></div>
												<div id="sejong"></div>
												<div id="gwangju"></div>
												<div id="jeonbuk"></div>
												<div id="jeonnam"></div>
												<div id="gyeongbuk"></div>
												<div id="gyeongnam"></div>
												<div id="daegu"></div>
												<div id="busan"></div>
												<div id="ulsan"></div>
												<div id="jeju"></div>
											</div>
											
											<div
												class="flex-fill card-pane-right pt-2 pb-2 pl-4 pr-4 homeGraph"
												id="branchFeeChartP">
												<!-- 여기가 옆쪽 -->
												<canvas id="branchFeeChart"
													class="chartjs-render-monitor col-md-8"></canvas>
											</div>
						 				</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!--  한국지도 -->
	<script type="text/javascript" src="/static/js/home/raphael_min.js"></script>
	<script type="text/javascript" src="/static/js/home/raphael_path_s.korea.js"></script>
</body>
<script src="/static/js/home/home.js"></script>
</html>