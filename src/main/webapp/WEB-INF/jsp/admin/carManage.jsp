<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
  <link
    rel="stylesheet"
    href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"
  />
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <head>
    <title>차량 관리 페이지</title>

  </head>
  <body>
    <section class="content-header">
      <div class="container-fluid ">
        <div class="row mb-2">
          <div class="col-md-2 col-md-6">
            <h1>차량 관리</h1>
          </div>
          <div class="col-md-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item">관리자 메뉴</li>
              <li class="breadcrumb-item active">차량 관리</li>
            </ol>
          </div>
        </div>
      </div>
      <!-- /.container-fluid -->
    </section>

    <div class="container-fluid vh-80">
      <div class="row">
        <div class="col-md-2">
          <div class="card height100">
            <div class="card-header">
              <h3 class="card-title" style="height: 30px">조직도</h3>
            </div>

            <div class="card-body small height675px">
              <div id="jstree" class="myScrollableBlock scrollbar-outer"></div>
            </div>
          </div>
        </div>

        <div class="col-md-10">
          <div class="card height100">
            <div class="card-header">
              <button
                class="btn btn-sm bg-olive"
                data-toggle="modal"
                data-target="#insertModal"
              >
                차량 추가
              </button>

              <div class="card-tools">
                <div class="input-group input-group-sm">
                  <select
                    class="col-md-4 small"
                    name="searchType"
                    id="searchType"
                  >
                    <option value="carNumber" class="small">차량번호</option>
                    <option value="carName" class="small">차종</option>
                  </select>

                  <input
                    class="col-sm-7 form-control form-control-navbar"
                    type="search"
                    placeholder="Search"
                    name="keyword"
                    id="keyword"
                    onkeypress="searchEnter()"
                  />
                  <div class="input-group-append">
                    <button
                      onclick="searchClick()"
                      class="btn btn-sm bg-olive"
                      name="btnSearch"
                      id="btnSearch"
                    >
                      <i class="fas fa-search"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <div class="card-body table-responsive p-0 height700px">
              <table class="table table-hover carCss" id="tableTest">
                <thead class="bg-lightgray">
                  <tr>
                    <th style="width: 15%">지역</th>
                    <th style="width: 20%">차량번호</th>
                    <th style="width: 20%">차종</th>
                    <th style="width: 20%">연비(km)</th>
                    <th style="width: 20%">구입날짜</th>
                    <th style="width: 5%">삭제</th>
                  </tr>
                </thead>

                <tbody id="tableListBody" class="small "></tbody>
              </table>
            </div>
          </div>
        </div>
        <div id="page"></div>
      </div>
    </div>
  </body>

  <%@include file="/WEB-INF/jsp/alert.jsp" %> <%@include
  file="carManageModal.jsp"%>

  <script src="/static/js/adminCarManage.js"></script>
</html>
