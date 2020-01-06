<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta
  content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
  name="viewport"
/>

<title>PathFinder</title>
<link rel="shortcut icon" href="/static/img/favicon.ico">

<!-- jQuery -->
<script src="/static/js/public/jquery.js"></script>

<!-- Bootstrap -->
<script src="/static/js/public/bootstrap.bundle.js"></script>

<!-- Font Awesome Icons -->
<link
  rel="stylesheet"
  href="/static/css/public/fontawesome-free/css/all.css"
/>

<!-- Google Font: Source Sans Pro -->
<link
  href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:300,400,500,700&display=swap&subset=korean"
  rel="stylesheet"
/>

<!-- Theme style -->
<script src="/static/dist/js/adminlte.js"></script>
<link rel="stylesheet" href="/static/dist/css/adminlte.css" />

<!-- ChartJS -->
<script src="https://www.chartjs.org/samples/latest/utils.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.bundle.js"></script>

<%-- bootstrap-select --%>
<link rel="stylesheet" href="/static/css/public/select2-bootstrap4.css">
<link href="/static/css/public/select2.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.0.12/dist/js/select2.full.js"></script>

<%-- paging --%>
<link rel="stylesheet" href="/static/css/public/paging.css" />
<script src="/static/js/public/paging.js"></script>

<!-- JsTree -->
<script src="//cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/jstree.min.js"></script>
<link rel="stylesheet" href="/static/css/jstree/style.css" />

<%-- daum map --%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- LeafLet Library -->
<link
  rel="stylesheet"
  href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
/>
<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"></script>

<!-- Leaflet routing-machine -->
<link
  rel="stylesheet"
  href="/static/css/route/leaflet-routing-machine.css"
/>
<script src="/static/js/route/leaflet-routing-machine.js"></script>

<%-- jquery validation --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/additional-methods.js"></script>

<%-- moment --%>
<script src="/static/js/public/moment.js"></script>

<%-- Calendar --%>
<link rel="stylesheet" href="/static/css/public/calendar.css" />
<script src="/static/js/public/calendar.js"></script>

<%-- jquery Loading --%>
<link rel="stylesheet" href="/static/css/public/loading.css" />
<script src="/static/js/public/loading.js"></script>

<%-- scrollBar --%>
<link rel="stylesheet" href="/static/css/public/jquery.scrollbar.css" />
<script src="/static/js/public/jquery.scrollbar.js"></script>

<!-- Alert -->
<link href="/static/css/public/alert.css" rel="stylesheet" />
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

<script type="text/javascript">
  window.getCookie = function(name) {
    let match = document.cookie.match(new RegExp("(^| )" + name + "=([^;]+)"));
    if (match) {
      return match[2];
    }
  };

  $(document).on("show.bs.modal", ".modal", function(event) {
  var zIndex = 1040 + 10 * $(".modal:visible").length;
  $(this).css("z-index", zIndex);
  setTimeout(function() {
    $(".modal-backdrop")
      .not(".modal-stack")
      .css("z-index", zIndex - 1)
      .addClass("modal-stack");
    }, 0);
  });

  $(document).ready(function() {
    $.ajaxSetup({
      beforeSend: function(req) {
        req.setRequestHeader(
          "Authorization",
          "pathfinder " + getCookie("token")
        );
      }
    });
  });
</script>
