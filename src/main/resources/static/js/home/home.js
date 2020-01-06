$(document).ready(function() {
  init();
});

function checkEvent() {
  $("#myDelivery").change(function() {
    let checked = $(this).prop("checked");

    drawDoughnut(checked);
  });
}

function initDoughnut(res) {
  let data = null;
  let total = res[0] + res[1] + res[2];

  if (total !== 0) {
    data = {
      datasets: [
        {
          data: [],
          backgroundColor: ["#CF4444", "#EBBB54", "#94ba65"]
        }
      ],
      labels: ["운송예정", "운송중", "운송완료"]
    };
  } else {
    data = {
      labels: ["Empty"],
      datasets: [
        {
          labels: "데이터가 존재하지 않습니다",
          backgroundColor: ["#D3D3D3"],
          data: [100]
        }
      ]
    };
  }

  let config = {
    type: "doughnut",
    data: data,
    options: {
      responsive: true,
      maintainAspectRatio: false,
      legend: {
        position: "top"
      },
      title: {
        display: true,
        text: "운송현황"
      },
      animation: {
        animateScale: true,
        animateRotate: true
      }
    }
  };

  $("#chart-area").remove();
  $("#doughnutDiv").append(`<canvas id="chart-area"
	 class="chart-js-render-monitor"></canvas>`);

  let ctx = $("#chart-area");

  myDoughnut = new Chart(ctx, config);
  myDoughnut.options.circumference = Math.PI;
  myDoughnut.options.rotation = -Math.PI;

  for (let i = 0; i < res.length; i++) {
    myDoughnut.data.datasets[0].data.push(res[i]);
  }

  myDoughnut.update();
}

function drawDoughnut(checked) {
  $.ajax({
    url: "/home/getTotalCount.do",
    type: "get",
    data: {
      myDelivery: checked
    },
    success: function(res) {
      let will = res[0];
      let ing = res[1];
      let pp = res[2];
      let total = will + ing + pp;

      initDoughnut(res);

      drawProgress(will, ing, pp, total);
    }
  });
}

function drawProgress(will, ing, pp, total) {
  $("#willProgress").html(`<b>${will}</b>/${total}`);
  $("#ingProgress").html(`<b>${ing}</b>/${total}`);
  $("#ppProgress").html(`<b>${pp}</b>/${total}`);

  calWill = 0;
  calIng = 0;
  calPp = 0;

  if (total !== 0) {
    calWill = parseInt((will / total) * 100);
    calIng = parseInt((ing / total) * 100);
    calPp = parseInt((pp / total) * 100);
  }

  $("#willDiv").animate(
    {
      width: `${calWill}%`
    },
    1000
  );
  $("#ingDiv").animate(
    {
      width: `${calIng}%`
    },
    1000
  );
  $("#ppDiv").animate(
    {
      width: `${calPp}%`
    },
    1000
  );
}

function recentlyHistory() {
  $.ajax({
    url: "/home/recentlyHistory",
    type: "get",

    success: function(res) {
      var str = "";
      if (res.resultCode !== "ERROR") {
        $.each(res.data, function(key, value) {
          str += `<tr class="tr-shadow">`;

          if (value.stat === -1)
            str +=
              '<td><span class="badge badge-secondary">운송완료</span></td>';
          else if (value.stat === 0)
            str += '<td><span class="badge badge-success">운송중</span></td>';
          else if (value.stat === 1)
            str += '<td><span class="badge badge-warning">운송예정</span></td>';
          else if (value.stat === 2)
        	str += '<td><span class="badge badge-success">운송중</span></td>';

          str += "<td data-title='출발일자'>" + value.dlvrdate + "</td>";
          str += "<td data-title='도착일자'>" + value.arrivedate + "</td>";
          str += "<td data-title='사용자'>" + value.username + "</td>";
          str += "<td data-title='출발지'>" + value.dep + "</td>";
          str += "<td data-title='도착지'>" + value.arvl + "</td>";
          str += "<td data-title='차량번호'>" + value.carname + "</td>";
          str += "</tr>";
        });
      } else {
        $("#tableListBody").html("");
        $("#page").html("");

        str += `<tr class="tr-shadow">`;
        str += `<td colspan="8">`;
        str += `${res.description}`;
        str += `</td>`;
        str += `</tr>`;
      }
      $("#schedule").html(str);
    }
  });
}

function todayHistory() {
  $.ajax({
    url: "/home/todayHistory",
    type: "get",
    success: function(res) {
      var str = "";

      if (res.resultCode !== "ERROR") {
        $.each(res.data, function(key, value) {
          str += `<tr class="tr-shadow">`;

          if (value.stat === -1)
            str +=
              '<td><span class="badge badge-secondary">운송완료</span></td>';
          else if (value.stat === 0)
            str += '<td><span class="badge badge-success">운송중</span></td>';
          else if (value.stat === 1)
            str += '<td><span class="badge badge-warning">운송예정</span></td>';
          else if (value.stat === 2)
            str += '<td><span class="badge badge-success">운송중</span></td>';
          str += "<td data-title='출발일자'>" + value.dlvrdate + "</td>";
          str += "<td data-title='도착일자'>" + value.arrivedate + "</td>";
          str += "<td data-title='사용자'>" + value.username + "</td>";
          str += "<td data-title='출발지'>" + value.dep + "</td>";
          str += "<td data-title='도착지'>" + value.arvl + "</td>";
          str += "<td data-title='차량번호'>" + value.carname + "</td>";
          str += "</tr>";
        });
      } else {
        $("#tableListBody").html("");
        $("#page").html("");

        str += `<tr class="tr-shadow">`;
        str += `<td colspan="8">`;
        str += `${res.description}`;
        str += `</td>`;
        str += `</tr>`;
      }
      $("#schedule").html(str);
    }
  });
}

function todayHistoryPercent() {
  $.ajax({
    url: "/home/todayHistoryPercent",
    type: "get",
    success: function(res) {
      if (res !== -1) {
        $("#todayPercent").html(res + "<small>%</small>");
      } else {
        $("#todayPercent").html(" -");
      }
    }
  });
}

function branchCount() {
  $.ajax({
    url: "/home/totalBranchCount",
    type: "get",
    data: {},
    success: function(res) {
      $("#branchCount").html(res + " 개");
    }
  });
}

function userCount() {
  $.ajax({
    url: "/home/totalUserCount",
    type: "get",
    data: {},
    success: function(res) {
      $("#userCount").html(res + " 명");
    }
  });
}

function historyTotalCount() {
  $.ajax({
    url: "/home/totalHistoryCount",
    type: "get",
    success: function(res) {
      $("#totalHistoryCount").html(res + " 개");
    }
  });
}

function init() {
  todayHistory();
  drawDoughnut(false);
  checkEvent();
  userCount();
  branchCount();
  historyTotalCount();
  todayHistoryPercent();
  loadingMap();
  sendBranchsKeyword(1);
}
