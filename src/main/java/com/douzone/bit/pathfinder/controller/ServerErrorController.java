package com.douzone.bit.pathfinder.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/error")
public class ServerErrorController implements ErrorController {

	@Override
	public String getErrorPath() {

		return "/error";
	}

	@GetMapping({"", "/"})
	public ModelAndView handleError(HttpServletRequest request) {

		ModelAndView errorPage = new ModelAndView();
		
		String errorMsg = "";
		
		int errorCode = getErrorCode(request);

		switch (errorCode) {
			case 400: {
				errorMsg = "잘못된 요청입니다.";
				break;
			}
			case 401: {
				errorMsg = "권한이 없습니다.";
				break;
			}
			case 404: {
				errorMsg = "해당 경로를 찾을 수 없습니다.";
				break;
			}
			case 405: {
				errorMsg = "잘못된 요청입니다.";
				break;
			}
			case 500: {
				errorMsg = "서버 오류입니다. 잠시후 다시 시도하세요.";
				break;
			}
			default : {
				errorMsg = "알수없는 오류입니다. 잠시후 다시 시도하세요.";
			}
		}

		errorPage.addObject("errorCode", "" + errorCode);
		errorPage.addObject("errorMsg", errorMsg);
		
		errorPage.setViewName("/defaultpage/error");
		
		return errorPage;
	}
	
	@GetMapping("/error_403")
	public ModelAndView accessDenied() {
		ModelAndView errorPage = new ModelAndView();
		
		errorPage.addObject("errorCode", "" + 403);
		errorPage.addObject("errorMsg", "권한이 없습니다.");
		errorPage.setViewName("/defaultpage/error");
		
		return errorPage;
	}

	private int getErrorCode(HttpServletRequest request) {
		return (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	}
}
