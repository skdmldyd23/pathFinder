(function($, window, document, undefined) {
  "use strict";
  function Paging(element, options) {
    this.element = element;
    this.options = {
      nowPage: options.nowPage || 1, // 현재 페이지 번호
      pageNum: options.pageNum, // 총 페이지 번호
      buttonNum: (options.buttonNum >= 5 ? options.buttonNum : 5) || 7, // 보여줄 페이지 버튼 개수
      callback: options.callback, // callback 함수
    };
    this.init();
  }
  Paging.prototype = {
    constructor: Paging,
    init: function() {
      this.createHtml();
      this.bindClickEvent();
      this.disabled();
    },
    createHtml: function() {
      var me = this;
      var nowPage = this.options.nowPage;
      var pageNum = this.options.pageNum;
      var buttonNum = this.options.buttonNum;
      var content = [];
      content.push("<ul>");
      if (pageNum !== 1 && pageNum !== 0){
    	  content.push("<li class='xl-prevPage'>이전</li>");
      }

      // 보여줄 버튼개수보다 페이지 버튼 수가 작으면 모두 보여줌
      if (pageNum <= buttonNum) {
        for (var i = 1; i <= pageNum; i++) {
          if (nowPage !== i) {
            content.push("<li>" + i + "</li>");
          } else {
            content.push("<li class='xl-active'>" + i + "</li>");
          }
        }
      } else if (nowPage <= Math.floor(buttonNum / 2)) {
        //현재 페이지가 전체 페이지 수의 절반보다 작으면 1부터 시작
        for (var i = 1; i <= buttonNum - 2; i++) {
          if (nowPage !== i) {
            content.push("<li>" + i + "</li>");
          } else {
            content.push("<li class='xl-active'>" + i + "</li>");
          }
        }
        content.push("<li class='xl-disabled'>...</li>");
        content.push("<li>" + pageNum + "</li>");
      } else if (pageNum - nowPage <= Math.floor(buttonNum / 2)) {
        // 현재 페이지가 페이지 총수의 절반보다 크면 아래로 숨김
        content.push("<li>" + 1 + "</li>");
        content.push("<li class='xl-disabled'>...</li>");
        for (var i = pageNum - buttonNum + 3; i <= pageNum; i++) {
          if (nowPage !== i) {
            content.push("<li>" + i + "</li>");
          } else {
            content.push("<li class='xl-active'>" + i + "</li>");
          }
        }
      } else {
        //앞쪽 페이지 번호
        if (nowPage - Math.floor(buttonNum / 2) <= 0) {
          for (var i = 1; i <= Math.floor(buttonNum / 2); i++) {
            if (nowPage !== i) {
              content.push("<li>" + i + "</li>");
            } else {
              content.push("<li class='xl-active'>" + i + "</li>");
            }
          }
        } else {
          content.push("<li>" + 1 + "</li>");
          content.push("<li class='xl-disabled'>...</li>");
          for (var i = nowPage - Math.floor(buttonNum / 2) + (buttonNum % 2 == 0 ? 3 : 2); i <= nowPage; i++) {
            if (nowPage !== i) {
              content.push("<li>" + i + "</li>");
            } else {
              content.push("<li class='xl-active'>" + i + "</li>");
            }
          }
        }
        //뒤쪽 페이지 번호
        if (pageNum - nowPage <= 0) {
          for (var i = nowPage + 1; i <= pageNum; i++) {
            content.push("<li>" + i + "</li>");
          }
        } else {
          for (var i = nowPage + 1; i <= nowPage + Math.floor(buttonNum / 2) - 2; i++) {
            content.push("<li>" + i + "</li>");
          }
          content.push("<li class='xl-disabled'>...</li>");
          content.push("<li>" + pageNum + "</li>");
        }
      }
      if (pageNum !== 1 && pageNum !== 0){
    	  content.push("<li class='xl-nextPage'>다음</li>");
      }
      content.push("</ul>");
      me.element.html(content.join(""));
      // DOM 재생성 후 매번 button 사용 금지 여부
      setTimeout(function() {
        me.disabled();
      }, 20);
    },
    bindClickEvent: function() {
      var me = this;
      me.element.off("click", "li");
      me.element.on("click", "li", function() {
        var cla = $(this).attr("class");
        var num = parseInt($(this).html());
        var nowPage = me.options.nowPage;
        if ($(this).hasClass("xl-disabled")) {
          return;
        }
        if (cla === "xl-prevPage") {
          if (nowPage !== 1) {
            me.options.nowPage -= 1;
          }
        } else if (cla === "xl-nextPage") {
          if (nowPage !== me.options.pageNum) {
            me.options.nowPage += 1;
          }
        } else {
          me.options.nowPage = num;
        }
        me.createHtml();
        if (me.options.callback) {
          me.options.callback(me.options.nowPage);
        }
      });
    },
    disabled: function() {
      var me = this;
      var nowPage = me.options.nowPage;
      var pageNum = me.options.pageNum;
      if (nowPage === 1) {
        me.element
          .children()
          .children(".xl-prevPage")
          .addClass("xl-disabled");
      } else if (nowPage === pageNum) {
        me.element
          .children()
          .children(".xl-nextPage")
          .addClass("xl-disabled");
      }
    },
  };
  $.fn.paging = function(options) {
    return new Paging($(this), options);
  };
})(jQuery, window, document);
