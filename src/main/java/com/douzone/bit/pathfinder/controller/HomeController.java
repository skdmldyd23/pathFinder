package com.douzone.bit.pathfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.response.AdminBranchResponse;
import com.douzone.bit.pathfinder.model.network.response.HistoryResponse;
import com.douzone.bit.pathfinder.service.AdminBranchService;
import com.douzone.bit.pathfinder.service.AdminUserService;
import com.douzone.bit.pathfinder.service.HistoryService;
import com.douzone.bit.pathfinder.service.HomeService;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	AdminUserService adminUserService;
	
	@Autowired
	AdminBranchService adminBranchService;
	
	@GetMapping({ "", "/" })
	public ModelAndView home(ModelAndView  mv, HttpServletRequest request) {
		mv.setViewName("/home");

		return mv;
	}
	
	@GetMapping("/recentlyHistory")
	public Header<List<HistoryResponse>> recentlyHistory(){
		return historyService.readRecentlyHistoryUseHome();
	}
	
	@GetMapping("/todayHistory")
	public Header<List<HistoryResponse>> todayHistory(){
		return historyService.readTodayHistoryUseHome();
	}
	
	@GetMapping("/getTotalCount.do")
	public int[] totalCount(
			@RequestParam("myDelivery") boolean myDelivery) {
		
		return homeService.getTotalCount(myDelivery);
	}

	// 오늘의 배송현황
	@GetMapping("/todayHistoryPercent")
	public double todayHistoryPercent() {

		return historyService.todayHistoryPercent();
	}

	// 전체사용자 수
	@GetMapping("/totalUserCount")
	public Long userCount() {

		return homeService.userCount();
	}

	// 전체지점 수
	@GetMapping("/totalBranchCount")
	public Long branchCount() {
		
		return homeService.branchCount();
	}

	// 전체히스토리 수
	@GetMapping("/totalHistoryCount")
	public Long historyCount() {

		return homeService.historyAll();
	}
	
	@GetMapping("/barChart")
	public  Header<List<AdminBranchResponse>> barChart(@RequestParam(value="keyword") Long keyword) {

		return homeService.barChart(keyword);
	}
	
	
}