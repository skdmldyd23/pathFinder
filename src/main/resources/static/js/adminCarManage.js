$(document).ready(function() {
  carlist();
  treeLoading();
});
// insertModal 닫힐 시
$("#insertModal").on("hidden.bs.modal", function() {
  $("#carCreateForm")[0].reset();
  // carCreateValid.resetForm();
  resetvalid("#carCreateForm");
});

function searchEnter() {
  if (window.event.keyCode == 13) {
    searchClick();
  }
}

// 검색버튼
function searchClick() {
  var treeId = sessionStorage.getItem("treeId");
  var url = "";
  url = url + "?searchType=" + $("select#searchType").val();
  url = url + "&keyword=" + $("#keyword").val();
  url = url + "&selectedArea=" + treeId;
  carsearch(url);
}

function resetvalid(formName) {
  $(formName)[0].reset();
  var length = $(formName)[0].length;
  var sclass = null;
  for (var i = 0; i < length; i++) {
    sclass = $(formName)[0][i].getAttribute("id");
    sclass = "#" + sclass;
    $(sclass).removeClass("is-invalid");
    $(sclass).removeClass("is-valid");
  }
}

/*
 * // 검색버튼 $('#btnSearch').click(function(e){ e.preventDefault(); var treeId =
 * sessionStorage.getItem("treeId"); var url = ""; url = url + "?searchType=" +
 * $('#searchType').val(); url = url + "&keyword=" + $('#keyword').val(); url =
 * url + "&selectedArea="+treeId; carsearch(url); });
 */

// 지역이름 숫자변환
function areaNameTrans(carArea) {
  switch (carArea) {
    case "서울":
      return 1;
    case "부산":
      return 2;
    case "대구":
      return 3;
    case "인천":
      return 4;
    case "광주":
      return 5;
    case "대전":
      return 6;
    case "울산":
      return 7;
    case "경기":
      return 8;
    case "강원":
      return 9;
    case "충북":
      return 10;
    case "충남":
      return 11;
    case "전북":
      return 12;
    case "전남":
      return 13;
    case "경북":
      return 14;
    case "경남":
      return 15;
    case "제주":
      return 16;
    case "세종":
      return 17;
  }
}
// 페이징
function pageButton(totalPages, currentPage) {
  $("#page").paging({
    nowPage: currentPage + 1,
    pageNum: totalPages,
    buttonNum: 12,
    callback: function(currentPage) {
      carlist(currentPage - 1);
    }
  });
}
// 검색페이징
function pageButton1(totalPages, currentPage, url) {
  $("#page").paging({
    nowPage: currentPage + 1,
    pageNum: totalPages,
    buttonNum: 12,
    callback: function(currentPage) {
      carsearch(url, currentPage - 1);
    }
  });
}

// 폼 내용 Json으로 변경
$.fn.serializeObject = function() {
  var result = {};
  var extend = function(i, element) {
    var node = result[element.name];
    if ("undefined" !== typeof node && node !== null) {
      if ($.isArray(node)) {
        node.push(element.value);
      } else {
        result[element.name] = [node, element.value];
      }
    } else {
      result[element.name] = element.value;
    }
  };
  $.each(this.serializeArray(), extend);
  return result;
};
// 검색뷰
function carsearch(searchUrl, searchpage = 0) {
  $.ajax({
    type: "GET",
    url: "/admin/carmanage/search" + searchUrl + "&page=" + searchpage,
    contentType: "application/json",
    success: function(res) {
      var str = "";
      var buttonAll = "";
      if (res.resultCode !== "ERROR") {
        $.each(res.data, function(key, value) {
          str += `<tr class="tr-shadow"><td>` + value.carArea + "</td>";
          str += "<td data-title='차량번호'>" + value.carNumber + "</td>";
          str += "<td data-title='차종'>" + value.carName + "톤 카고</td>";
          str += "<td data-title='연비(km)'>" + value.carFuel + " km </td>";
          str +=
            "<td data-title='구입날짜'>" +
            value.carBuy.year +
            "." +
            value.carBuy.month +
            "." +
            value.carBuy.day +
            "</td>";
          str += "<td data-title='삭제'><div class='table-data-feature'>";
          str +=
            `<button class="item btn btn-primary-outline btn-sm" data-toggle="tooltip" data-placement="top" title="Delete" onclick="cardelete(` +
            value.carIndex +
            `, '` +
            value.carName +
            `', '` +
            value.carArea +
            `')"><i class="fas fa-trash-alt"></i></button>`;
          str += `</td>'+ '</tr>`;
        });
        buttonAll +=
          '<button id="allSearchB" onclick="allSearch()">전체보기</button>';
        pageButton1(
          res.pagination.totalPages,
          res.pagination.currentPage,
          searchUrl
        );
      } else {
        str += `<tr class="tr-shadow">`;
        str += `<td colspan="8">`;
        str += `${res.description}`;
        str += `</td>`;
        str += `</tr>`;
      }

      $("#seachAll").html(buttonAll);
      $("#tableListBody").html(str);
    }
  });
}

// 추가
function carinsert(insertData, carea) {
  $.ajax({
    type: "POST",
    url: "/admin/carmanage",
    data: insertData,
    contentType: "application/json",
    success: function(data) {
      var treeId = sessionStorage.getItem("treeId");
      if (treeId !== "company:1") {
        var url = "";
        url =
          url + "?searchType=area&keyword=" + carea + "&selectedArea=" + treeId;
        carsearch(url);
      } else {
        carlist();
      }

      $("#updateTitle").text("등록 성공");
      $("#updateMessage").text("해당 차량을 추가하였습니다.");
      $("#updateAlertModal").modal("show");
    },
    error: function(request, status, error) {
      $("#errorMessage").text(
        "code:" +
          request.status +
          "\n" +
          "message:" +
          request.responseText +
          "\n" +
          "error:" +
          error
      );
      $("#failModal").modal("show");
    }
  });
}

function deleteCheckModal(idx, carname, carea) {
  $("#checkOk").on("click", function() {
    cardelete(idx, carname, carea);
  });

  $("#checkTitle").text("확인");
  $("#checkMessage").text("해당 차량을 삭제하시겠습니까?");
  $("#checkModal").modal("show");
}

// 삭제
function cardelete(idx, carname, carea) {
  $.ajax({
    type: "DELETE",
    url: "/admin/carmanage/delete/" + idx,
    data: {},
    success: function() {
      var treeId = sessionStorage.getItem("treeId");
      if (treeId !== "company:1") {
        let Cname = areaNameTrans(carea);
        var url = "";
        url =
          url + "?searchType=area&keyword=" + Cname + "&selectedArea=" + treeId;
        carsearch(url);
      } else {
        carlist();
      }

      $("#updateTitle").text("삭제 성공");
      $("#updateMessage").text("해당 차량을 삭제하였습니다.");
      $("#updateAlertModal").modal("show");
    }
  });
}

// 첫페이지
function carlist(selectPage) {
  $.ajax({
    url: "/admin/carmanage/carlist.do?page=" + selectPage,
    type: "get",
    data: {},
    success: function(res) {
      var str = "";
      $.each(res.data, function(key, value) {
        str += `<tr class="tr-shadow"><td>` + value.carArea + "</td>";
        str += "<td data-title='차량번호'>" + value.carNumber + "</td>";
        str += "<td data-title='차종'>" + value.carName + "톤 카고</td>";
        str += "<td data-title='연비(km)'>" + value.carFuel + " km </td>";
        str +=
          "<td data-title='구입날짜'>" +
          value.carBuy.year +
          "." +
          value.carBuy.month +
          "." +
          value.carBuy.day +
          "</td>";
        str += "<td data-title='삭제'><div class='table-data-feature'>";
        str +=
          `<button class="item btn btn-primary-outline btn-sm" data-toggle="tooltip" data-placement="top" title="Delete" onclick="deleteCheckModal(` +
          value.carIndex +
          `, '` +
          value.carName +
          `')"><i class="fas fa-trash-alt"></i></button>`;
        str += `</td>'+ '</tr>`;
      });
      $("#tableListBody").html(str);
      pageButton(res.pagination.totalPages, res.pagination.currentPage);
    }
  });
}
// jstree 로딩
function treeLoading() {
  $("#jstree").jstree({
    plugins: ["wholerow"],
    core: {
      themes: {
        name: "proton",
        reponsive: true
      },
      data: function(node, callback) {
        callback(treeData(node.id));
      }
    }
  });

  // jstree 값 받아오기
  function treeData(id) {
    var result = "";
    $.ajax({
      url: "/admin/branchmanage/treelist.do",
      type: "get",
      data: { id: id },
      async: false,
      success: function(res) {
        result = res.data;
      }
    });
    return result;
  }
  $("#jstree")
    .on("select_node.jstree", function(e, data) {
      var id = data.instance.get_node(data.selected).id;
      sessionStorage.setItem("treeId", id);
      let vid = id.slice(5);
      if (vid != "ny:1") {
        if (data.node.children.length > 0) {
          $("#jstree")
            .jstree(true)
            .toggle_node(data.node);
        }
        var url = "";
        url = url + "?searchType=area&keyword=" + vid + "&selectedArea=" + id;
        carsearch(url);
      } else {
        carlist();
      }
    })
    .bind("open_node.jstree", function(e, data) {
      var nodesToKeepOpen = [];

      nodesToKeepOpen.push(data.node.id);
      nodesToKeepOpen.push(data.node.parent);

      $(".jstree-node").each(function() {
        if (nodesToKeepOpen.indexOf(this.id) === -1) {
          $("#jstree")
            .jstree()
            .close_node(this.id);
        }
      });
    });
}

$("#branch_Addr").on("propertychange change keyup paste input", function() {
  var $el = $(":focus");
  $(this).blur();
  $el.focus();
});

// ! Modal 관련 =======================
// TODO JavaScript 다시 선언시 Const 중복 에러발생
var insertModal = $("#insertModal");

// insertModal 열릴 시
insertModal.on("shown.bs.modal", function() {
  $("#myInput").trigger("focus");
  areaLoading(insertModal);
});

// insertModal 닫힐 시
insertModal.on("hidden.bs.modal", function() {
  $("#carCreateForm")[0].reset();
  insertModal.find(".formCheck").html("");

  let str = "<option value='' disabled selected>선택</option>";

  insertModal.find("#carArea").html(str);
  // .selectpicker("refresh");

  // insertModal.find("#carNumber").selectpicker("refresh");
  // insertModal.find("#carName").selectpicker("refresh");
  // insertModal.find("#carFuel").selectpicker("refresh");
  // insertModal.find("#carBuy").selectpicker("refresh");
  $("#carCreateForm")
    .validate()
    .resetForm();
});

// 모달 내에서 지역 선택 시
insertModal.find("#carArea").change(function() {
  let selected = $(this)
    .children("option:selected")
    .val();

  // branchLoading(insertModal, selected);
});

$("#calendar").calendar({
  width: 280,
  height: 280,
  trigger: "#carBuy",
  zIndex: 999,
  format: "yyyy-mm-dd",
  weekArray: ["일", "월", "화", "수", "목", "금", "토"],
  monthArray: [
    "1월",
    "2월",
    "3월",
    "4월",
    "5월",
    "6월",
    "7월",
    "8월",
    "9월",
    "10월",
    "11월",
    "12월"
  ],
  onClose: function(view, date, data) {
    $("#carBuy").val(moment(date).format("YYYY-MM-DD"));
  }
});

// 모달 내 지역 로딩
function arrayToObject(array) {
  return array.reduce(function(result, item) {
    let obj = {};
    obj.id = item[0];
    obj.text = item[1];

    result.push(obj);

    return result;
  }, []);
}

$("select").on("select2:open", function() {
  $(".select2-results__options").addClass("scrollbar-outer");
  $(".select2-results__options").scrollbar();
});

function areaLoading(modal) {
  $.ajax({
    url: "/admin/usermanage/arealist.do",
    type: "get",
    async: false,
    success: function(res) {
      let areaData = arrayToObject(res.data);

      modal.find("#carArea").html("<option></option>");
      modal.find("#carArea").select2({
        width: "100%",
        placeholder: "지역 선택",
        data: areaData,
        theme: "bootstrap4",
        style: "height:100px"
      });

      selectInit(modal);
    },
    error: function(request, status, error) {
      $("#errorMessage").text(
        "code:" +
          request.status +
          "\n" +
          "message:" +
          request.responseText +
          "\n" +
          "error:" +
          error
      );
      $("#failModal").modal("show");
    }
  });
}

const selectInit = modal => {
  // init
  modal.find("#carArea").select2({
    width: "100%",
    placeholder: "지역 선택",
    theme: "bootstrap4"
  });

  modal.find("#carName").select2({
    width: "100%",
    placeholder: "차종 선택",
    theme: "bootstrap4"
  });
};

// ! validation ====================
// select 포커스 문제 해결
$("select").on("select2:close", function() {
  $(this).blur();
});

var carInsertValid = $("#carCreateForm").validate({
  onkeyup: false,
  ignore: ":hidden",
  errorClass: "is-invalid",
  validClass: "is-valid",
  rules: {
    carArea: {
      required: true
    },

    carName: {
      required: true
    },
    carNumber: {
      required: true,
      rangelength: [7, 9],
      pattern: /^([가-힣ㄱ-ㅎㅏ-ㅣ\x20]{2})?\d{2}[가-힣ㄱ-ㅎㅏ-ㅣ\x20]\d{4}$/,
      // 중복체크
      remote: "/admin/carmanage/carcheck.do"
    },
    carFuel: {
      required: true
    },
    carBuy: {
      required: true,
      pattern: /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/
    }
  },
  messages: {
    carNumber: {
      remote: "등록된 차량번호입니다."
    }
  },

  // 에러 위치 조정
  errorPlacement: function(error, element) {},

  // valid 실패시
  invalidHandler: function(form, validator) {
    var errors = validator.numberOfInvalids();

    if (errors) {
      validator.errorList[0].element.focus();
    }
  },

  // valid 성공시
  submitHandler: function(form) {
    event.preventDefault();
    var formData = $("[name=carCreateForm]").serializeObject();
    carinsert(JSON.stringify(formData), formData.carArea);
    $("#insertModal").modal("hide");
    carInsertValid.resetForm();
    return false;
  },
  highlight: function(element, errorClass, validClass) {
    $(element)
      .addClass(errorClass)
      .removeClass(validClass);
  },
  unhighlight: function(element, errorClass, validClass) {
    $(element)
      .removeClass(errorClass)
      .addClass(validClass);
  }
});

$("#InsertTest").on('click', function() {
    $("#carArea").val(1);
    $("#carArea").trigger("change");
    $("#carNumber").val("01가1234");
    
    $("#carName").val(2);
    $("#carName").trigger("change");
    
    $("#carFuel").val(7);
    
    $("#carBuy").val("2020-01-08");
})