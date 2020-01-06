package com.douzone.bit.pathfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.request.AdminCarRequest;
import com.douzone.bit.pathfinder.model.network.response.AdminCarResponse;
import com.douzone.bit.pathfinder.service.AdminBranchService;
import com.douzone.bit.pathfinder.service.AdminCarService;

@RestController
@RequestMapping("/admin/carmanage")
public class AdminCarController {

	@GetMapping("")
	public ModelAndView carManage(ModelAndView mv, HttpServletRequest request) {

		mv.setViewName("/admin/carManage");
		return mv;
	}

	@Autowired
	AdminCarService adminCarService;

	@Autowired
	AdminBranchService adminBranchService;

	// @Autowired
	// AdminUserService adminUserService;
	//
	// branch create
	@PostMapping("")
	public Header<AdminCarResponse> carCreate(@RequestBody AdminCarRequest request) {

		return adminCarService.create(request);
	}

	@GetMapping("/search")
	public Header<List<AdminCarResponse>> carSearch(
			@RequestParam(required = false, defaultValue = "carName") String searchType,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "company:1") String selectedArea,
			@PageableDefault(sort = "carIndex", direction = Sort.Direction.DESC) Pageable pageable) {
				
		return adminCarService.search(pageable, searchType, keyword, selectedArea);
	}

	// 차량추가 중복확인
	@GetMapping("/carcheck.do")
	public Boolean idCheck(@RequestParam String carNumber) {
		return adminCarService.carCheck(carNumber);
	}

	// Car data
	@GetMapping("/carlist.do")
	public Header<List<AdminCarResponse>> carList(
			@PageableDefault(sort = "carIndex", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

		return adminCarService.listpage(pageable);
	}

	// Car delete
	@DeleteMapping("/delete/{carIndex}")
	public Header carDelete(@PathVariable Long carIndex) {
		return adminCarService.delete(carIndex);
	}
	//
}
