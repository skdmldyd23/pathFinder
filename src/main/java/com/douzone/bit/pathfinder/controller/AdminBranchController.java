package com.douzone.bit.pathfinder.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.entity.BranchTb;
import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.request.AdminBranchRequest;
import com.douzone.bit.pathfinder.model.network.response.AdminBranchResponse;
import com.douzone.bit.pathfinder.model.network.response.HierarchyResponse;
import com.douzone.bit.pathfinder.service.AdminBranchService;
import com.douzone.bit.pathfinder.service.AdminUserService;

@RestController
@RequestMapping("/admin/branchmanage")
public class AdminBranchController {

	@Autowired
	AdminBranchService adminBranchService;

	@Autowired
	AdminUserService adminUserService;

	// branch create
	@PostMapping("")
	public Header<AdminBranchResponse> branchCreate(@RequestBody @Valid AdminBranchRequest request,
			BindingResult bindingResult) {
		return adminBranchService.create(request);
	}

	// branch read
	@GetMapping("/read/{branchIndex}")
	public Optional<BranchTb> read(@PathVariable Long branchIndex) {
		return adminBranchService.read(branchIndex);
	}

	// branch view
	@GetMapping({ "", "/" })
	public ModelAndView branchManage(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/admin/branchManage");
		return mv;
	}

	// branch search
	@GetMapping("/search")
	public Header<List<AdminBranchResponse>> branchSearch(
			@RequestParam(required = false, defaultValue = "branchName") String searchType,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "company:1") String selectedArea,
			@PageableDefault(sort = "branchIndex", direction = Sort.Direction.DESC) Pageable pageable) {
		
		return adminBranchService.search(pageable, searchType, keyword, selectedArea);
	}

	// 지점추가 중복확인
	@GetMapping("/branchcheck.do")
	public Boolean idCheck(@RequestParam String branchName) {

		return adminBranchService.branchCheck(branchName);
	}

	// 트리 불러오기
	@GetMapping("/treelist.do")
	public Header<HierarchyResponse> treeList() {
		return adminBranchService.readCompany();
	}

	// branch data
	@GetMapping("/branchlist.do")
	public Header<List<AdminBranchResponse>> branchList(
			@PageableDefault(sort = "branchIndex", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
		return adminBranchService.listpage(pageable);
	}

	// branch update
	@PutMapping("/update")
	public Header<AdminBranchResponse> branchUpdate(@RequestBody AdminBranchRequest request) {
		return adminBranchService.update(request);
	}

	// branch delete
	@DeleteMapping("/delete/{branchIndex}")
	public Header branchDelete(@PathVariable Long branchIndex) {
		return adminBranchService.delete(branchIndex);
	}

}