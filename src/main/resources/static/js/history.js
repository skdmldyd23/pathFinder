$(document).ready(function() {
  getHistory(0, "ing");
  checkEvent();
  datePicker();
});

function pageButton(totalPages, currentPage, id) {
  $("#page").paging({
    nowPage: currentPage + 1,
    pageNum: totalPages,
    buttonNum: 12,
    callback: function(currentPage) {
      getHistory(currentPage - 1, id);
    }
  });
}

function datePicker() {
  $("#calendar").calendar({
    width: 280,
    height: 280,
    trigger: "#keyword",
    zIndex: 999,
    format: "yyyy-mm-dd",
    offset: [-95, 3],
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
      $("#keyword").val(moment(date).format("YYYY-MM-DD"));
    }
  });
}

function checkEvent(selectPage, id) {
  $("#myhistory").change(function() {
    $(this).prop("checked");
    let tabId = sessionStorage.getItem("tabId");

    getHistory(0, tabId);
  });
}

function getHistory(selectPage, id) {
  sessionStorage.setItem("tabId", id);

  printHistory(selectPage, id);
}

function printHistory(selectPage, id, keyword) {
  let isChecked = $("#myhistory").bootstrapToggle()[0].checked;

  $.ajax({
    url: "/history/gethistory.do",
    type: "get",
    data: {
      page: selectPage,
      id: id,
      myhistory: isChecked,
      keyword: keyword
    },
    success: function(res) {
      let str = "";

      if (res.resultCode !== "ERROR") {
        $.each(res.data, function(key, value) {
          str += `<tr class="tr-shadow">`;
          str += "<td >" + value.regdate + "</td>";
          str += "<td data-title='사용자'>" + value.username + "</td>";
          str += "<td data-title='출발지'>" + value.dep + "</td>";
          str += "<td data-title='도착지'>" + value.arvl + "</td>";
          str += "<td data-title='출발일자'>" + value.dlvrdate + "</td>";
          str += "<td data-title='도착일자'>" + value.arrivedate + "</td>";
          str += "<td data-title='차량번호'>" + value.carname + "</td>";
          str +=
            "<td data-title='상세보기'><button class='btn btn-sm btn-outline-success'" +
            "data-toggle='modal' data-target='#detailsModal'" +
            "onclick='getRoutes(" +
            JSON.stringify(value) +
            ")'><i class='fas fa-search'></i>상세보기</button></td>";
          str += "</tr>";
        });
        pageButton(res.pagination.totalPages, res.pagination.currentPage, id);
      } else {
        $("#tableListBody").html("");
        $("#page").html("");

        str += `<tr class="tr-shadow">`;
        str += `<td colspan="8">`;
        str += `${res.description}`;
        str += `</td>`;
        str += `</tr>`;
      }
      $("#tableListBody").html(str);
    }
  });
}

function getSearch() {
  let tabId = sessionStorage.getItem("tabId");
  let keyword = $("#keyword").val();

  printHistory(0, tabId, keyword);
}

// Modal
var detailsModal = $("#detailsModal");

detailsModal.on("shown.bs.modal", function() {
  $(this).css("overflow-y", "auto");
});

function deleteCheckModal(history) {
  $("#checkOk").on("click", function() {
    removeRoutes(history);
  });

  $("#checkTitle").text("확인");
  $("#checkMessage").text("해당 기록을 삭제하시겠습니까?");
  $("#checkModal").modal("show");
}

function removeRoutes(history) {
  $.ajax({
    url: "/history/removeroutes.do",
    type: "delete",
    data: JSON.stringify(history.id),
    contentType: "application/json; charset=UTF-8",
    success: function(res) {
      $("#updateTitle").text("삭제 성공");
      $("#updateMessage").text("해당 기록을 삭제하였습니다.");
      $("#updateAlertModal").modal("show");

      $("#detailsModal").modal("toggle");

      let tabId = sessionStorage.getItem("tabId");
      getHistory(0, tabId);
    }
  });
}

function getRoutes(routes) {
  let imgSrc = routes.imgSrc;

  $.ajax({
    url: "/history/getroutes.do",
    type: "get",
    data: {
      routesIndex: routes.routes
    },
    success: function(res) {
      let str = "";
      let count = 0;
      $.each(res.data, function(key, value) {
        str += `<tr id="ModalTr">`;
        str += "<td data-title='번호'><b>" + ++count + "</b></td>";
        str += "<td data-title='출발지'>" + value.rdep + "</td>";
        str += "<td data-title='도착지'>" + value.rarvl + "</td>";
        str += "<td data-title='거리'>" + value.rdist + " km" + "</td>";
        str += "<td data-title='시간'>" + value.rtime + "</td>";
        str += "<td data-title='비용'>" + value.rfee.addComma() + "원</td>";
        str += "</tr>";
      });

      if (userAuth === "[ROLE_ADMIN]" || userName === routes.username) {
        $("#deleteBtn").show();
      } else {
        $("#deleteBtn").hide();
      }

      $("#deleteBtn")
        .off()
        .on("click", function() {
          deleteCheckModal(routes);
        });

      $("#routesListBody").html(str);

      detailsModal.find("#mapImg").attr("src", routes.imgSrc);
      detailsModal.find("#totalTime").text(routes.time);
      detailsModal.find("#regdate").text(routes.regdate);
      detailsModal.find("#username").text(routes.username);
      detailsModal.find("#carname").text(routes.carname);
      detailsModal.find("#dep").text(routes.dep);
      detailsModal.find("#arvl").text(routes.arvl);
      detailsModal.find("#sortType").text(routes.sortType + "순");
      detailsModal.find("#dist").text(routes.dist.addComma() + " km");
      detailsModal.find("#dist").text(routes.dist.addComma() + " km");
      detailsModal.find("#fee").text(routes.fee.addComma() + " 원");
    }
  });
}

$("#printBtn").on("click", function() {
  let domClone = $("#printThis").clone();
  let $printSection = $("#printSection").get(0);

  if (!$printSection) {
    $printSection = $('<div id="printSection"></div>');

    $(document.body).append($printSection);
  }

  $("#printSection").html("");
  $("#printSection").append(domClone);

  let tbody = $("#printSection")
    .find("tbody")
    .html();

  $("#printSection")
    .find("thead")
    .after(tbody);
  $("#printSection")
    .find(".scrollbar-outer")
    .remove();

  window.print();
});

//! 유틸부분 =======================
Number.prototype.addComma = function() {
  var regexp = /\B(?=(\d{3})+(?!\d))/g;
  return this.toString().replace(regexp, ",");
};
