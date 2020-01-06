<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Error Page</title>

<!-- BootStrap -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.min.css">

<!-- Ionicons -->
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">

<!-- Theme style -->
<link rel="stylesheet" href="/static/css/error.css">

<!-- Google Font: Source Sans Pro -->
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700"
	rel="stylesheet">
</head>

<body>
	<div class="container">
		<div class="row h-100">
			<div class="col-sm-12 my-auto">
				<!-- Content Wrapper. Contains page content -->
				<!-- Main content -->
				<section class="content">
					<div class="error-page">
						<h2 class="headline text-warning">${errorCode}</h2>

						<div class="error-content">
							<h3>
								<i class="fas fa-exclamation-triangle text-warning"></i>
								${errorMsg}
							</h3>
							<p>
								에러가 발생하여 페이지를 불러올 수 없습니다. <br>
								<a href="javascript:history.back()">여기</a>를 클릭하여
									이전 페이지로 돌아갈 수 있습니다.
							</p>
						</div>
						<!-- /.error-content -->
					</div>
					<!-- /.error-page -->
				</section>
				<!-- /.content -->
			</div>
		</div>
	</div>
</body>
</html>