package com.douzone.bit.pathfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.response.AdminUserResponse;
import com.douzone.bit.pathfinder.model.network.response.HierarchyResponse;
import com.douzone.bit.pathfinder.service.AdminUserService;
import com.douzone.bit.pathfinder.service.HierarchyService;

@RestController
@RequestMapping("/hierarchy")
public class HierarchyController {

	@Autowired
	HierarchyService hierarchyService;

	@Autowired
	AdminUserService adminUserService;
	
	@GetMapping({ "", "/" })
	public ModelAndView getHierarchy(ModelAndView mv, HttpServletRequest request) {

		mv.setViewName("/hierarchy");
		return mv;
	}
	@GetMapping("/treelist.do")
	public Header<HierarchyResponse> treeList() {

		return hierarchyService.readCompany();
	}

	@GetMapping(value = "/userlist.do", params = {"id"})
	public Header<List<AdminUserResponse>> userList(
			@RequestParam("id") String id,
			@PageableDefault(sort = { "userIndex" },
			direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

		return adminUserService.list(id, pageable, "", "");
	}
	
	@GetMapping(value = "/userlist.do", params = {"id", "searchType", "searchValue"})
	public Header<List<AdminUserResponse>> userList(
			@RequestParam("id") String id,
			@RequestParam("searchType") String searchType,
			@RequestParam("searchValue") String searchValue,
			@PageableDefault(sort = { "userIndex" },
			direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
		
		return adminUserService.search(id, searchType, searchValue, pageable);
	}
}