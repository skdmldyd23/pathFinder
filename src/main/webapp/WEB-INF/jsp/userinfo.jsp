<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
  <head>
    <title>Document</title>
  </head>
  <body>
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Profile</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="/home">홈</a></li>
              <li class="breadcrumb-item active">내 정보</li>
            </ol>
          </div>
        </div>
      </div>
      <!-- /.container-fluid -->
    </section>
    <section class="content">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-5">
            <!-- Profile Image -->
            <div class="card card-primary card-outline">
              <div class="card-body box-profile">
                <div class="text-center">
                  <div class="ribbon-wrapper ribbon-rg">
                    <div class="ribbon bg-info text-rg">
                      Info
                    </div>
                  </div>
                </div>
                <h3 class="profile-username text-center">
                  <sec:authentication property="principal.userFullName" />
                </h3>
                <p class="text-muted text-center">더존공장</p>

                <ul class="list-group list-group-unbordered mb-3">
                  <li class="ml-3 mr-3 list-group-item">
                    <b>이름</b>
                    <a class="float-right mr-3"
                      ><sec:authentication property="principal.userFullName"
                    /></a>
                  </li>
                  <li class="ml-3 mr-3 list-group-item">
                    <b>ID</b>
                    <a class="float-right mr-3"
                      ><sec:authentication property="principal.username"
                    /></a>
                  </li>
                  <li class="ml-3 mr-3 list-group-item">
                    <b>이메일</b>
                    <a class="float-right mr-3"
                      ><sec:authentication property="principal.userEmail"
                    /></a>
                  </li>
                  <li class="ml-3 mr-3 list-group-item">
                    <b>전화번호</b>
                    <a class="float-right mr-3"
                      ><sec:authentication property="principal.userPhone"
                    /></a>
                  </li>
                </ul>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->

            <!-- About Me Box -->
            <div class="card card-primary">
              <div class="card-header">
                <h3 class="card-title">About Me</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <h6><i class="fas fa-map-marker-alt ml-1"></i> 지역</h6>

                <p class="text-muted ml-4">
                  <sec:authentication property="principal.userArea" />
                </p>
                <hr />

                <h6><i class="fas fa-building ml-1 pt-3"></i> 지점</h6>

                <p class="text-muted">
                  <span class="tag tag-danger ml-4"
                    ><sec:authentication property="principal.userBranch"
                  /></span>
                </p>

                <hr />
                <h6><i class="fas fa-user ml-1 pt-3"></i>직책</h6>

                <p class="text-muted ml-4">
                  <sec:authentication property="principal.userPosition" />
                </p>

                <hr />
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
          <div class="col-md-7">
            <div class="card">
              <div class="card-header">
                <div class="widget-user-header">
                  <div class="nav-link">
                    <h4><b>정보 변경</b></h4>
                  </div>
                </div>
              </div>
              <!-- /.card-header -->

              <div class="card-body">
                <div class="tab-pane" id="settings">
                  <form class="form-horizontal" id="settings" name="settings">
                    <div class="form-group row">
                      <input hidden id="userIndex" value= "<sec:authentication
                        property="principal.userIndex"
                      />" id="userIndex" name="userIndex">
                      <label for="inputName" class="col-sm-2 col-form-label"
                        >이름</label
                      >
                      <div class="col-sm-10">
                        <input type="email" class="form-control" id="userName"
                        name="userName" placeholder="Name" readonly
                        value="<sec:authentication
                          property="principal.userFullName"
                        />">
                      </div>
                    </div>
                    <div class="form-group row">
                      <label for="inputID" class="col-sm-2 col-form-label"
                        >아이디</label
                      >
                      <div class="col-sm-10">
                        <input type="email" class="form-control" id="userId"
                        name="userId" placeholder="ID" readonly
                        value="<sec:authentication
                          property="principal.username"
                        />">
                      </div>
                    </div>
                    <div class="form-group row">
                      <label for="inputPassword" class="col-sm-2 col-form-label"
                        >비밀번호</label
                      >
                      <div class="col-sm-10">
                        <input
                          type="password"
                          class="form-control"
                          id="userPw"
                          name="userPw"
                          placeholder="비밀번호"
                        />
                      </div>
                    </div>
                    <div class="form-group row">
                      <label
                        for="inputPasswordCheck"
                        class="col-sm-2 col-form-label"
                        >비밀번호 확인</label
                      >
                      <div class="col-sm-10">
                        <input
                          type="password"
                          class="form-control"
                          id="userPwCheck"
                          name="userPwCheck"
                          placeholder="비밀번호 확인"
                        />
                      </div>
                    </div>
                    <div class="form-group row">
                      <label for="inputEmail" class="col-sm-2 col-form-label"
                        >이메일</label
                      >
                      <div class="col-sm-10">
                        <input type="text" class="form-control" id="userEmail"
                        name="userEmail" placeholder="Email"
                        value="<sec:authentication
                          property="principal.userEmail"
                        />">
                      </div>
                    </div>
                    <div class="form-group row">
                      <label for="inputPhone" class="col-sm-2 col-form-label"
                        >전화번호</label
                      >
                      <div class="col-sm-10">
                        <input type="text" class="form-control" id="userPhone"
                        name="userPhone" placeholder="Phone"
                        value="<sec:authentication
                          property="principal.userPhone"
                        />">
                      </div>
                    </div>
                    <div class="form-group row">
                      <div class="offset-sm-2 col-sm-10">
                        <button
                          type="submit"
                          class="btn btn-success float-right"
                        >
                          수정
                        </button>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="tab-content">
                  <!-- /.tab-pane -->

                  <!-- /.tab-pane -->
                </div>
                <!-- /.tab-content -->
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.nav-tabs-custom -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </div>
      <!-- /.container-fluid -->
    </section>
    <script src="/static/js/userInfo.js"></script>
  </body>

  <%@include file="alert.jsp"%>
</html>
