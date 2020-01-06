package com.douzone.bit.pathfinder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.bit.pathfinder.model.network.Header;
import com.douzone.bit.pathfinder.model.network.request.AdminUserRequest;
import com.douzone.bit.pathfinder.model.network.response.AdminBranchResponse;
import com.douzone.bit.pathfinder.model.network.response.AdminUserResponse;
import com.douzone.bit.pathfinder.model.network.response.HierarchyResponse;
import com.douzone.bit.pathfinder.service.AdminBranchService;
import com.douzone.bit.pathfinder.service.AdminUserService;
import com.douzone.bit.pathfinder.service.HierarchyService;

@RestController
@RequestMapping("/admin/usermanage")
public class AdminUserController {

	@Autowired
	AdminUserService adminUserService;

	@Autowired
	AdminBranchService adminBranchService;

	@Autowired
	HierarchyService hierarchyService;

	// 회원 리스트 뷰
	@GetMapping({ "", "/" })
	public ModelAndView userManage(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/admin/userManage");

		return mv;
	}

	// 회원 한명 정보 불러오기 (update에서 사용)
	@GetMapping("/userread.do")
	public Header<AdminUserResponse> userRead(@RequestParam Long userIndex) {

		return adminUserService.read(userIndex);
	}

	// 회원 리스트 불러오기
	@GetMapping("/userlist.do")
	public Header<List<AdminUserResponse>> userList(@RequestParam String treeId,
			@RequestParam(required = false, defaultValue = "") String searchType,
			@RequestParam(required = false, defaultValue = "") String keyword,
			@PageableDefault(sort = { "userIndex" },
			direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

		return adminUserService.list(treeId, pageable, searchType, keyword);
	}
	
	@GetMapping("/idcheck.do")
	public Boolean idCheck(@RequestParam String userId) {

		return adminUserService.idCheck(userId);
	}

	// 트리 불러오기
	@GetMapping("/treelist.do")
	public Header<HierarchyResponse> treeList() {

		return hierarchyService.readCompany();
	}

	// 지점 리스트 불러오기
	@GetMapping("/branchlist.do")
	public Header<List<Object>> branchList(@RequestParam Long areaIndex) {

		return adminUserService.readBranchName(areaIndex);
	}

	// 지역 리스트 불러오기
	@GetMapping("/arealist.do")
	public Header<List<Object>> areaList() {

		return adminUserService.readAreaName();
	}

	// 회원 등록
	@PostMapping("")
	public Header<AdminUserResponse> create(@RequestBody @Valid AdminUserRequest request, Errors errors) {

		if (errors.hasErrors()) {
			return Header.ERROR(errors);
		}

		return adminUserService.create(request);
	}

	// 회원 수정
	@PutMapping("")
	public Header<AdminUserResponse> userUpdate(@RequestBody @Valid AdminUserRequest request, Errors errors) {

		if (errors.hasErrors()) {
			return Header.ERROR(errors);
		}

		return adminUserService.update(request);
	}

	// 비밀번호 초기화
	@PatchMapping("")
	public Header<AdminUserResponse> userPwUpdate(@RequestParam Long userIndex) {

		return adminUserService.updatePw(userIndex);
	}

	// 회원 삭제
	@DeleteMapping("")
	public Header userDelete(@RequestParam Long userIndex) {

		return adminUserService.delete(userIndex);
	}
	

}