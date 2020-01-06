$.fn.serializeObject = function() {
  let result = {};
  let extend = function(i, element) {
    let node = result[element.name];
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
  return JSON.stringify(result);
};
// 회원 수정
function userUpdate(req) {
  $.ajax({
    url: "/userinfo",
    type: "put",
    contentType: "application/json",
    data: req,
    success: function(res) {
      $("#updateTitle").text("수정 성공");
      $("#updateMessage").text("유저정보를 수정하였습니다.");
      $("#updateAlertModal").modal("show");

      $("#updateAlertModalOk").on("click", function() {
        window.location.href = "/userinfo";
      });
    },
    error: function(request, status, error) {
      alert(
        "code:" +
          request.status +
          "\n" +
          "message:" +
          request.responseText +
          "\n" +
          "error:" +
          error
      );
    }
  });
}

$("form").each(function() {
  $(this).validate({
    onkeyup: false,
    ignore: ":hidden, [readonly]",
    errorClass: "is-invalid",
    validClass: "is-valid",
    rules: {
      userIndex: {
        required: true
      },
      userPw: {
        required: true,
        pattern: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/
      },
      userPwCheck: {
        required: true,
        equalTo: "#userPw"
      },
      userEmail: {
        required: true,
        email: true
      },
      userPhone: {
        required: true,
        pattern: /^\d{3}-\d{4}-\d{4}$/
      }
    },
    messages: {
      userPw: {
        required: "비밀번호를 입력하세요.",
        pattern: "문자,숫자,특수문자(!@#$%^&*?) 혼합 8자 이상"
      },
      userPwCheck: {
        required: "비밀번호 확인을 입력해주세요",
        equalTo: " 비밀번호가 다릅니다."
      },
      userEmail: {
        required: "이메일을 입력하세요.",
        email: "이메일 형식이 맞지 않습니다."
      },
      userPhone: {
        required: "연락처를 입력하세요.",
        pattern: "연락처 형식이 맞지 않습니다."
      }
    },

    // valid 실패시
    invalidHandler: function(form, validator) {
      var errors = validator.numberOfInvalids();

      if (errors) {
        validator.errorList[0].element.focus();
      }
      console.log(errors);
    },

    // valid 성공시
    submitHandler: function(form) {
      const formId = $(form).attr("id");
      var req = $(form).serializeObject();
      userUpdate(req);
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
});
